package com.realitix.mealassistant.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.util.MealUtil

class ImageRepository(val context: Context) {
    fun insert(i: ImageRaw) {
        return MealDatabase.getInstance(context).imageDao().insert(i)
    }

    fun get(uuid: String): Image? {
        return MealDatabase.getInstance(context).imageDao().get(uuid)
    }
}