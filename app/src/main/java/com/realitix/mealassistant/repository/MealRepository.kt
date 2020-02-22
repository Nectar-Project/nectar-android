package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.dao.MealDao
import com.realitix.mealassistant.database.entity.Meal
import kotlin.math.floor

class MealRepository(val context: Context) {
    fun listMeals(timestamp: Long): LiveData<List<Meal>> {
        val nbSeconds: Long = 86400
        val begin: Long = floor((timestamp / nbSeconds).toDouble()).toLong() * nbSeconds
        val end = begin + nbSeconds
        return MealDatabase.getInstance(context).mealDao().search(begin, end)
    }

    suspend fun hasMeal(mealId: Long): Boolean {
        if(MealDatabase.getInstance(context).mealDao().has(mealId) != null)
            return true
        return false
    }

    fun getMealFull(mealId: Long): LiveData<MealDao.MealFull> {
        return MealDatabase.getInstance(context).mealDao().getFull(mealId)
    }

    suspend fun createMeal(meal: Meal): Long {
        return MealDatabase.getInstance(context).mealDao().insert(meal)
    }

    companion object {
        private var instance: MealRepository? = null
        @Synchronized
        fun getInstance(context: Context): MealRepository {
            if (instance == null) {
                instance = MealRepository(context)
            }
            return instance as MealRepository
        }
    }
}