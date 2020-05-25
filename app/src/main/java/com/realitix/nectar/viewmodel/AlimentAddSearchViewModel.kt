package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.MealAlimentRaw
import com.realitix.nectar.database.entity.ReceipeStepAlimentStateRaw
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.MealAlimentRepository
import com.realitix.nectar.repository.ReceipeStepAlimentStateRepository
import com.realitix.nectar.util.EntityType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlimentAddSearchViewModel(
    private val rAliment: AlimentRepository,
    private val rReceipeStepAlimentState: ReceipeStepAlimentStateRepository,
    private val rMealAliment: MealAlimentRepository,
    private val objUuid: String,
    private val entityType: EntityType
) : ViewModel() {
    private val alimentSearchTerm: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val aliments = alimentSearchTerm.switchMap { rAliment.search(it) }

    fun searchAliments(name: String) {
        alimentSearchTerm.value = name
    }

    fun create(alimentUuid: String, quantity: Int) {
        when(entityType) {
            EntityType.RECEIPE -> createReceipeStepAliment(alimentUuid, quantity)
            EntityType.MEAL -> createMealAliment(alimentUuid, quantity)
            else -> throw Exception()
        }
    }

    private fun createReceipeStepAliment(alimentStateUuid: String, quantity: Int) {
        val c = ReceipeStepAlimentStateRaw(objUuid, alimentStateUuid, quantity)
        GlobalScope.launch {
            rReceipeStepAlimentState.insertSuspend(c)
        }
    }

    private fun createMealAliment(alimentUuid: String, quantity: Int) {
        val c = MealAlimentRaw(objUuid, alimentUuid, quantity)
        GlobalScope.launch {
            rMealAliment.insertSuspend(c)
        }
    }
}