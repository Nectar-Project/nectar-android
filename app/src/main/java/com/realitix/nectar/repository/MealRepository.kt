package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.MealDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.MealUtil

class MealRepository(val context: Context) {
    fun listMeals(timestamp: Long): LiveData<List<Meal>> = MealDatabase.getInstance(context).mealDao().search(
        MealUtil.beginDayTimestamp(timestamp), MealUtil.endDayTimestamp(timestamp))

    suspend fun hasMeal(mealUuid: String): Boolean {
        if(MealDatabase.getInstance(context).mealDao().get(mealUuid) != null)
            return true
        return false
    }

    fun getMealLive(mealUuid: String): LiveData<Meal> {
        return MealDatabase.getInstance(context).mealDao().getLive(mealUuid)
    }

    fun getMeal(uuid: String): Meal? = MealDatabase.getInstance(context).mealDao().get(uuid)
    fun insertMeal(meal: MealRaw) = MealDatabase.getInstance(context).mealDao().insert(meal)
    fun insertMealAliment(aliment: MealAlimentRaw) = MealDatabase.getInstance(context).mealAlimentDao().insert(aliment)
    fun insertMealReceipe(receipe: MealReceipeRaw) = MealDatabase.getInstance(context).mealReceipeDao().insert(receipe)

    suspend fun createMeal(meal: Meal) {
        return MealDatabase.getInstance(context).mealDao().insert(meal)
    }

    suspend fun createMealAliment(aliment: MealAliment) {
        return MealDatabase.getInstance(context).mealAlimentDao().insert(aliment)
    }

    suspend fun createMealReceipe(receipe: MealReceipe) {
        return MealDatabase.getInstance(context).mealReceipeDao().insert(receipe)
    }
}