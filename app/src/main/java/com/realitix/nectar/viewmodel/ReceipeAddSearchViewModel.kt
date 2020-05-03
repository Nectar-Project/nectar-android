package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.MealReceipe
import com.realitix.nectar.database.entity.MealReceipeRaw
import com.realitix.nectar.database.entity.ReceipeStepReceipe
import com.realitix.nectar.database.entity.ReceipeStepReceipeRaw
import com.realitix.nectar.repository.MealReceipeRepository
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepReceipeRepository
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.MealReceipeEnum
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReceipeAddSearchViewModel (
    private val rReceipe: ReceipeRepository,
    private val rReceipeStepReceipe: ReceipeStepReceipeRepository,
    private val rMealReceipe: MealReceipeRepository,
    private val objUuid: String,
    private val entityType: EntityType
) : ViewModel() {
    private val receipeSearchTerm: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val receipes = receipeSearchTerm.switchMap { rReceipe.search(it) }

    fun searchReceipes(name: String) {
        receipeSearchTerm.value = name
    }

    fun create(linkedReceipeUuid: String, quantity: Float) {
        when(entityType) {
            EntityType.RECEIPE -> createReceipeStepReceipe(linkedReceipeUuid, quantity)
            EntityType.MEAL -> createMealReceipe(linkedReceipeUuid, quantity)
            else -> throw Exception()
        }
    }

    private fun createReceipeStepReceipe(linkedReceipeUuid: String, quantity: Float) {
        val c = ReceipeStepReceipeRaw(objUuid, linkedReceipeUuid, quantity)
        GlobalScope.launch {
            rReceipeStepReceipe.insertSuspend(c)
        }
    }

    private fun createMealReceipe(linkedReceipeUuid: String, quantity: Float) {
        val c = MealReceipeRaw(objUuid, linkedReceipeUuid, quantity)
        GlobalScope.launch {
            rMealReceipe.insertSuspend(c)
        }
    }
}