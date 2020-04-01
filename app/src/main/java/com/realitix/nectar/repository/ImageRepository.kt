package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.MealDatabase
import com.realitix.nectar.database.entity.*

class ImageRepository(val context: Context) {
    fun insert(i: ImageRaw) {
        return MealDatabase.getInstance(context).imageDao().insert(i)
    }

    fun get(uuid: String): Image? {
        return MealDatabase.getInstance(context).imageDao().get(uuid)
    }
}