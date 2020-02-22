package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import androidx.navigation.NavController
import com.realitix.mealassistant.database.entity.Meal
import com.realitix.mealassistant.repository.MealRepository
import kotlinx.coroutines.runBlocking

class MealsViewModel (var repository: MealRepository, var navController: NavController) : ViewModel() {

    fun createMeal(timestamp: Long): Long {
        var rid: Long = -1
        runBlocking {
            rid = repository.createMeal(Meal(timestamp, 1, "Description"))
        }
        return rid
    }
}