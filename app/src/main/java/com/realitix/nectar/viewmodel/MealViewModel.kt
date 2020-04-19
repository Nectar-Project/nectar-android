package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Meal
import com.realitix.nectar.repository.MealRepository

class MealViewModel constructor(var rMeal: MealRepository, mealUuid: String) : ViewModel() {
    val meal: LiveData<Meal> = rMeal.getLive(mealUuid)
}