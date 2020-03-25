package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.MealUtil.Companion.generateUuid
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReceipeViewModel constructor(val repository: ReceipeRepository, receipeUuid: String) : ViewModel() {
    val receipe: LiveData<Receipe> = repository.getReceipeFull(receipeUuid)

    fun updateReceipeName(newName: String) {
        val r = receipe.value!!.getReceipeName()
        r.name = newName
        viewModelScope.launch {
            repository.updateReceipeName(r)
        }
    }
    fun createStep(description: String) = viewModelScope.launch { repository.createReceipeStep(ReceipeStep(generateUuid(), receipe.value!!.uuid, 0, description, 0)) }
}