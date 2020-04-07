package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.launch

class ReceipeViewModel (
    private val receipeRepository: ReceipeRepository,
    private val stringKeyRepository: StringKeyRepository,
    receipeUuid: String
) : ViewModel() {
    val receipe: LiveData<Receipe> = receipeRepository.getReceipeFull(receipeUuid)

    fun updateReceipeName(newName: String) {
        viewModelScope.launch {
            val keyValue = stringKeyRepository.getValue(receipe.value!!.nameUuid, "fr")!!
            keyValue.value = newName
            stringKeyRepository.updateValue(keyValue)
        }
    }

    fun createStep(description: String) {
        viewModelScope.launch {
            receipeRepository.createReceipeStep(ReceipeStep(generateUuid(), receipe.value!!.uuid, 0, description, 0))
        }
    }
}