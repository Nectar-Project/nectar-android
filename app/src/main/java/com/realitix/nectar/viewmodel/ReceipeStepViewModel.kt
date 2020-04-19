package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepRepository

class ReceipeStepViewModel constructor(
    rReceipe: ReceipeRepository,
    rReceipeStep: ReceipeStepRepository,
    receipeUuid: String,
    stepUuid: String
) : ViewModel() {
    val receipe: LiveData<Receipe> = rReceipe.getLive(receipeUuid)
    val step: LiveData<ReceipeStep> = rReceipeStep.getLive(stepUuid)
}