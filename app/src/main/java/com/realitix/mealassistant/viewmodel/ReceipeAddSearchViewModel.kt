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
    val objId: Long,
    val enumId: Int
) : ViewModel() {
    private val receipeSearchTerm: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val receipes = receipeSearchTerm.switchMap { receipeRepository.search(it) }

    fun searchReceipes(name: String) {
        receipeSearchTerm.value = name
    }

    fun create(linkedReceipeId: Long) {
        when (enumId) {
            MealReceipeEnum.RECEIPE -> createReceipeStepReceipe(linkedReceipeId)
            MealReceipeEnum.MEAL -> createMealReceipe(linkedReceipeId)
        }
    }

    private fun createReceipeStepReceipe(linkedReceipeId: Long) {
        val c = ReceipeStepReceipe(linkedReceipeId, objId)
        GlobalScope.launch {
            receipeRepository.createReceipeStepReceipe(c)
        }
    }

    private fun createMealReceipe(linkedReceipeId: Long) {
        val c = MealReceipe(linkedReceipeId, objId)
        GlobalScope.launch {
            mealRepository.createMealReceipe(c)
        }
    }
}