package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Meal
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
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