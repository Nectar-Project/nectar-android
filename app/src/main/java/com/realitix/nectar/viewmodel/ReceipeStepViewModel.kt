package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReceipeStepViewModel constructor(
    private val rReceipeStep: ReceipeStepRepository,
    private val rStringKeyValue: StringKeyValueRepository,
    private val stepUuid: String
) : ViewModel() {
    val step: LiveData<ReceipeStep> = rReceipeStep.getLive(stepUuid)

    fun deleteStep() {
        viewModelScope.launch {
            rReceipeStep.deleteSuspend(step.value!!)
        }
    }

    fun updateStepDescription(description: String) {
        viewModelScope.launch {
            val keyValue = rStringKeyValue.getSuspend(step.value!!.descriptionUuid, "fr")!!
            keyValue.value = description
            rStringKeyValue.updateSuspend(keyValue)
        }
    }

    fun updateStepTime(duration: Int) {
        viewModelScope.launch {
            val s = rReceipeStep.getSuspend(stepUuid)!!
            s.duration = duration
            rReceipeStep.updateSuspend(s)
        }
    }

    fun getAllSteps(): List<ReceipeStep> {
        val receipeUuid = step.value!!.receipeUuid
        return runBlocking {
            val allSteps = rReceipeStep.listByReceipeSuspend(receipeUuid)
            // Remove current step
            val steps = mutableListOf<ReceipeStep>()
            for(s in allSteps) {
                if(s.uuid != stepUuid) {
                    steps.add(s)
                }
            }

            steps
        }
    }

    fun setPreviousStep(previousStep: ReceipeStep?) {
        viewModelScope.launch {
            val s = rReceipeStep.getSuspend(stepUuid)!!
            s.previousStepUuid = previousStep?.uuid
            rReceipeStep.updateSuspend(s)
        }
    }
}