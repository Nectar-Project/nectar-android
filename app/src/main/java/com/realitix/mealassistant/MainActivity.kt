package com.realitix.mealassistant

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setupWithNavController(findNavController(this, R.id.nav_host_fragment))

        startAlimentUpdaterWorker()
    }

    private fun startAlimentUpdaterWorker() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        /*val alimentUpdaterWork = OneTimeWorkRequestBuilder<AlimentUpdaterWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(alimentUpdaterWork)*/
    }

    override fun onSupportNavigateUp() = findNavController(this, R.id.nav_host_fragment).navigateUp()

    fun toggleKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
