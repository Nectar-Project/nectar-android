package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.dao.MealDao
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.database.entity.Meal
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MealViewModel constructor(var repository: MealRepository, mealId: Long) : ViewModel() {
    val meal: LiveData<MealDao.MealFull> = {
        var rid = mealId
        runBlocking {
            if(!repository.hasMeal(rid)) {
                val r = Meal(0, 1, "Description")
                rid = repository.createMeal(r)
            }
        }
        repository.getMealFull(rid)
    }()
}