package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Meal
import com.realitix.mealassistant.repository.MealRepository

class MealViewModel constructor(var repository: MealRepository, mealUuid: String) : ViewModel() {
    val meal: LiveData<Meal> = repository.getMealLive(mealUuid)
}