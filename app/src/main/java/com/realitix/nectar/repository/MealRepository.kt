package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class MealRepository(val context: Context) {
    fun listMeals(timestamp: Long): LiveData<List<Meal>> = NectarDatabase.getInstance(context).mealDao().search(
        NectarUtil.beginDayTimestamp(timestamp), NectarUtil.endDayTimestamp(timestamp))

    suspend fun hasMeal(mealUuid: String): Boolean {
        if(NectarDatabase.getInstance(context).mealDao().get(mealUuid) != null)
            return true
        return false
    }

    fun getMealLive(mealUuid: String): LiveData<Meal> {
        return NectarDatabase.getInstance(context).mealDao().getLive(mealUuid)
    }

    fun getMeal(uuid: String): Meal? = NectarDatabase.getInstance(context).mealDao().get(uuid)
    fun insertMeal(meal: MealRaw) = NectarDatabase.getInstance(context).mealDao().insert(meal)
    fun insertMealAliment(aliment: MealAlimentRaw) = NectarDatabase.getInstance(context).mealAlimentDao().insert(aliment)
    fun insertMealReceipe(receipe: MealReceipeRaw) = NectarDatabase.getInstance(context).mealReceipeDao().insert(receipe)

    suspend fun createMeal(meal: Meal) {
        return NectarDatabase.getInstance(context).mealDao().insert(meal)
    }

    suspend fun createMealAliment(aliment: MealAliment) {
        return NectarDatabase.getInstance(context).mealAlimentDao().insert(aliment)
    }

    suspend fun createMealReceipe(receipe: MealReceipe) {
        return NectarDatabase.getInstance(context).mealReceipeDao().insert(receipe)
    }
}