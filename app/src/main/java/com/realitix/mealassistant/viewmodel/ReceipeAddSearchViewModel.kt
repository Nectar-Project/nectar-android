package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.MealReceipe
import com.realitix.mealassistant.database.entity.ReceipeStepReceipe
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.MealReceipeEnum
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReceipeAddSearchViewModel (
    private val receipeRepository: ReceipeRepository,
    private val mealRepository: MealRepository,
    private val objUuid: String,
    private val enumId: Int
) : ViewModel() {
    private val receipeSearchTerm: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val receipes = receipeSearchTerm.switchMap { receipeRepository.search(it) }

    fun searchReceipes(name: String) {
        receipeSearchTerm.value = name
    }

    fun create(linkedReceipeUuid: String) {
        when (enumId) {
            MealReceipeEnum.RECEIPE -> createReceipeStepReceipe(linkedReceipeUuid)
            MealReceipeEnum.MEAL -> createMealReceipe(linkedReceipeUuid)
        }
    }

    private fun createReceipeStepReceipe(linkedReceipeUuid: String) {
        val c = ReceipeStepReceipe(linkedReceipeUuid, objUuid)
        GlobalScope.launch {
            receipeRepository.createReceipeStepReceipe(c)
        }
    }

    private fun createMealReceipe(linkedReceipeUuid: String) {
        val c = MealReceipe(linkedReceipeUuid, objUuid)
        GlobalScope.launch {
            mealRepository.createMealReceipe(c)
        }
    }
}