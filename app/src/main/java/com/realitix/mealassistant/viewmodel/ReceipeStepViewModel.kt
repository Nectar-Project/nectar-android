package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.repository.ReceipeRepository

class ReceipeStepViewModel constructor(var repository: ReceipeRepository, receipeUuid: String, stepUuid: String) : ViewModel() {
    val receipe: LiveData<Receipe> = repository.getReceipeLive(receipeUuid)
    val step: LiveData<ReceipeStep> = repository.getReceipeStepFull(stepUuid)
}