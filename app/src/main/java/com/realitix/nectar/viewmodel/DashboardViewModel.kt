package com.realitix.nectar.viewmodel


import androidx.lifecycle.ViewModel
import com.realitix.nectar.repository.*
import kotlinx.coroutines.runBlocking

class DashboardViewModel(
    val rMeal: MealRepository,
    timestamp: Long
): ViewModel() {
    fun computeShoppingList(begin: Long, end: Long) {
        runBlocking {
            val meals = rMeal.listRange(begin, end).value!!
            for(meal in meals) {

            }
        }
    }
}