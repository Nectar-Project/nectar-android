package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.entity.Meal
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.MealUtil.Companion.generateUuid
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MealPagerViewModel constructor(val repository: MealRepository, timestamp: Long) : ViewModel() {
    val meals: LiveData<List<Meal>> = repository.listMeals(timestamp)

    fun createMeal(timestamp: Long): String {
        val rid = generateUuid()
        runBlocking {
            repository.createMeal(Meal(rid, timestamp, 1, "Description"))
        }
        return rid
    }
}