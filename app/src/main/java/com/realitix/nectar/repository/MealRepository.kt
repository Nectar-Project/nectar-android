package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class MealRepository(val context: Context): GenericCrudRepository<MealRaw, Meal>() {
    override fun getDao() = NectarDatabase.getInstance(context).mealDao()

    fun listDay(timestamp: Long): LiveData<List<Meal>> = getDao().search(
        NectarUtil.beginDayTimestamp(timestamp),
        NectarUtil.endDayTimestamp(timestamp))
}