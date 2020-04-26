package com.realitix.nectar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.realitix.nectar.background.GitRepositoryBackgroundThread
import com.realitix.nectar.background.GitRepositorySynchronizer
import com.realitix.nectar.database.NectarDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class MainActivity: AppCompatActivity() {
    companion object {
        var enableSynchronizer = true
    }

    private lateinit var gitSynchronizer: GitRepositoryBackgroundThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fix bug with instrumented tests (force database reload)
        NectarDatabase.reloadInstance()
        setContentView(R.layout.activity_main)
        navigation.setupWithNavController(findNavController(this, R.id.nav_host_fragment))
        createNotificationChannel()
    }

    override fun onResume() {
        super.onResume()
        if(enableSynchronizer) {
            gitSynchronizer = GitRepositoryBackgroundThread(this)
            gitSynchronizer.start()
        }
    }

    override fun onPause() {
        if(enableSynchronizer) {
            gitSynchronizer.stopOnNextIteration()
        }
        super.onPause()
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

    fun toggleKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("nectar", "nectar", importance).apply {
            description = "nectar"
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
