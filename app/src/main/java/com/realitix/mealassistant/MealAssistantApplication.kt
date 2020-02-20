package com.realitix.mealassistant

import android.app.Application
import android.content.res.Configuration
import java.util.*


class MealAssistantApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Set locale in French
        val locale = Locale("fr")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )
    }
}