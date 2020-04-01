package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.repository.ReceipeRepository

class ReceipeStepViewModel constructor(var repository: ReceipeRepository, receipeUuid: String, stepUuid: String) : ViewModel() {
    val receipe: LiveData<Receipe> = repository.getReceipeLive(receipeUuid)
    val step: LiveData<ReceipeStep> = repository.getReceipeStepFull(stepUuid)
}