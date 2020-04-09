package com.realitix.nectar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.realitix.nectar.background.GitRepositorySynchronizer
import com.realitix.nectar.database.NectarDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


class MainActivity: AppCompatActivity() {
    private lateinit var gitSynchronizer: GitRepositorySynchronizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fix bug with instrumented tests
        NectarDatabase.reloadInstance()

        setContentView(R.layout.activity_main)
        navigation.setupWithNavController(findNavController(this, R.id.nav_host_fragment))
    }

    override fun onPause() {
        gitSynchronizer.stopOnNextIteration()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        gitSynchronizer = GitRepositorySynchronizer(this)
        gitSynchronizer.start()
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

    fun toggleKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
