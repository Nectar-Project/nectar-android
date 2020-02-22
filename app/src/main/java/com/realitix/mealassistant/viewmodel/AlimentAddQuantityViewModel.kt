package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.ReceipeStepAliment
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AlimentAddQuantityViewModel (
    private val receipeRepository: ReceipeRepository,
    alimentRepository: AlimentRepository,
    val alimentId: Long,
    val stepId: Long
) : ViewModel() {
    val aliment: LiveData<Aliment> = alimentRepository.getAliment(alimentId)

    fun createReceipeStepAliment(quantity: Int) {
        val c = ReceipeStepAliment(alimentId, stepId, quantity)
        GlobalScope.launch {
            receipeRepository.createReceipeStepAliment(c)
        }
    }
}