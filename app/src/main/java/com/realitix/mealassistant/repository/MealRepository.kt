package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.dao.MealDao
import com.realitix.mealassistant.database.entity.Meal
import com.realitix.mealassistant.database.entity.MealAliment
import com.realitix.mealassistant.database.entity.MealReceipe
import com.realitix.mealassistant.util.MealUtil

class MealRepository(val context: Context) {
    fun listMeals(timestamp: Long): LiveData<List<Meal>> = MealDatabase.getInstance(context).mealDao().search(
        MealUtil.beginDayTimestamp(timestamp), MealUtil.endDayTimestamp(timestamp))

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

    suspend fun createMealAliment(aliment: MealAliment): Long {
        return MealDatabase.getInstance(context).mealAlimentDao().insert(aliment)
    }

    suspend fun createMealReceipe(receipe: MealReceipe): Long {
        return MealDatabase.getInstance(context).mealReceipeDao().insert(receipe)
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