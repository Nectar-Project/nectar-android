package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.database.entity.StringKeyRaw
import com.realitix.nectar.database.entity.StringKeyValueRaw
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.launch

class ReceipeViewModel (
    rReceipe: ReceipeRepository,
    private val rReceipeStep: ReceipeStepRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository,
    receipeUuid: String
) : ViewModel() {
    val receipe: LiveData<Receipe> = rReceipe.getLive(receipeUuid)

    fun updateReceipeName(newName: String) {
        viewModelScope.launch {
            val keyValue = rStringKeyValue.getSuspend(receipe.value!!.nameUuid, "fr")!!
            keyValue.value = newName
            rStringKeyValue.updateSuspend(keyValue)
        }
    }

    fun createStep(description: String) {
        viewModelScope.launch {
            val sid = generateUuid()
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValueRaw(sid, "fr", description))
            rReceipeStep.insertSuspend(ReceipeStep(generateUuid(), receipe.value!!.uuid, null, sid, 0))
        }
    }
}