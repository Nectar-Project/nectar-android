package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.MealReceipe
import com.realitix.nectar.database.entity.ReceipeStepReceipe
import com.realitix.nectar.repository.MealReceipeRepository
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepReceipeRepository
import com.realitix.nectar.util.MealReceipeEnum
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReceipeAddSearchViewModel (
    private val rReceipe: ReceipeRepository,
    private val rReceipeStepReceipe: ReceipeStepReceipeRepository,
    private val rMealReceipe: MealReceipeRepository,
    private val objUuid: String,
    private val enumId: Int
) : ViewModel() {
    private val receipeSearchTerm: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val receipes = receipeSearchTerm.switchMap { rReceipe.search(it) }

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
            rReceipeStepReceipe.insertSuspend(c)
        }
    }

    private fun createMealReceipe(linkedReceipeUuid: String) {
        val c = MealReceipe(linkedReceipeUuid, objUuid)
        GlobalScope.launch {
            rMealReceipe.insertSuspend(c)
        }
    }
}