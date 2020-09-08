package com.realitix.nectar.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.NectarUtil
import kotlinx.coroutines.runBlocking

class DashboardViewModel(
    val rMeal: MealRepository,
    val rReceipeStep: ReceipeStepRepository,
    timestamp: Long
): ViewModel() {
    fun computeShoppingList(begin: Long, end: Long) {
        runBlocking {
            val res = mutableListOf<Pair<Aliment, Int>>()
            val meals = rMeal.listRangeSuspend(begin, end)
            for(meal in meals) {
                NectarUtil.addAlimentListToList(res, meal.listAliments(rReceipeStep))
            }
            Log.e("toto", res.toString())
        }
    }
}