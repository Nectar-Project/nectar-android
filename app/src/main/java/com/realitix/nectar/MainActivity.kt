package com.realitix.nectar

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.work.*
import com.realitix.nectar.work.GitRepositoryWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setupWithNavController(findNavController(this, R.id.nav_host_fragment))

        startGitRepositoryWorker()
    }

    private fun startGitRepositoryWorker() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val gitWorker = PeriodicWorkRequestBuilder<GitRepositoryWorker>(1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag("git")
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("gitSync", ExistingPeriodicWorkPolicy.REPLACE, gitWorker)
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

    fun toggleKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
