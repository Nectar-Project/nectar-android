package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.MealAliment
import com.realitix.mealassistant.database.entity.ReceipeStepAliment
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.MealReceipeEnum.Companion.MEAL
import com.realitix.mealassistant.util.MealReceipeEnum.Companion.RECEIPE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlimentAddQuantityViewModel (
    private val receipeRepository: ReceipeRepository,
    private val mealRepository: MealRepository,
    alimentRepository: AlimentRepository,
    private val alimentUuid: String,
    private val objUuid: String,
    private val enumId: Int
) : ViewModel() {
    val aliment: LiveData<Aliment> = alimentRepository.getAliment(alimentUuid)

    fun create(quantity: Int) {
        when (enumId) {
            RECEIPE -> createReceipeStepAliment(quantity)
            MEAL -> createMealAliment(quantity)
        }
    }

    private fun createReceipeStepAliment(quantity: Int) {
        val c = ReceipeStepAliment(alimentUuid, objUuid, quantity)
        GlobalScope.launch {
            receipeRepository.createReceipeStepAliment(c)
        }
    }

    private fun createMealAliment(quantity: Int) {
        val c = MealAliment(alimentUuid, objUuid, quantity)
        GlobalScope.launch {
            mealRepository.createMealAliment(c)
        }
    }
}