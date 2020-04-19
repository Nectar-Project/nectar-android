package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.MealAliment
import com.realitix.nectar.database.entity.ReceipeStepAliment
import com.realitix.nectar.database.entity.ReceipeStepAlimentRaw
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.MealReceipeEnum.Companion.MEAL
import com.realitix.nectar.util.MealReceipeEnum.Companion.RECEIPE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlimentAddQuantityViewModel (
    private val rReceipeStepAliment: ReceipeStepAlimentRepository,
    private val rMealAliment: MealAlimentRepository,
    rAliment: AlimentRepository,
    private val alimentUuid: String,
    private val objUuid: String,
    private val enumId: Int
) : ViewModel() {
    val aliment: LiveData<Aliment> = rAliment.getLive(alimentUuid)

    fun create(quantity: Int) {
        when (enumId) {
            RECEIPE -> createReceipeStepAliment(quantity)
            MEAL -> createMealAliment(quantity)
        }
    }

    private fun createReceipeStepAliment(quantity: Int) {
        val c = ReceipeStepAlimentRaw(alimentUuid, objUuid, quantity)
        GlobalScope.launch {
            rReceipeStepAliment.insertSuspend(c)
        }
    }

    private fun createMealAliment(quantity: Int) {
        val c = MealAliment(alimentUuid, objUuid, quantity)
        GlobalScope.launch {
            rMealAliment.insertSuspend(c)
        }
    }
}