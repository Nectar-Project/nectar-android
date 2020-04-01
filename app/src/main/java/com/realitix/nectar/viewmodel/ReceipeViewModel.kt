package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.launch

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