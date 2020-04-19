package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Meal
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.runBlocking

class MealPagerViewModel constructor(val rMeal: MealRepository, timestamp: Long) : ViewModel() {
    val meals: LiveData<List<Meal>> = rMeal.listDay(timestamp)

    fun createMeal(timestamp: Long): String {
        val rid = generateUuid()
        runBlocking {
            rMeal.insertSuspend(Meal(rid, timestamp, 1, "Description"))
        }
        return rid
    }
}