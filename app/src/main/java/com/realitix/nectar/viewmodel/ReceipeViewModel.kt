package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReceipeViewModel (
    private val rReceipe: ReceipeRepository,
    private val rReceipeStep: ReceipeStepRepository,
    private val rReceipeMeasure: ReceipeMeasureRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository,
    private val rMeasure: MeasureRepository,
    private val receipeUuid: String
) : ViewModel() {
    val receipe: LiveData<Receipe> = rReceipe.getLive(receipeUuid)

    fun getAllMeasures(): List<Measure> {
        return runBlocking {
            rMeasure.listSuspend()
        }
    }

    fun insertMeasure(name: String) {
        runBlocking {
            val sid = generateUuid()
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValueRaw(sid, "fr", name))
            rMeasure.insertSuspend(MeasureRaw(generateUuid(), sid))
        }
    }

    fun insertReceipeMeasure(measureUuid: String, quantity: Float) {
        viewModelScope.launch {
            rReceipeMeasure.insertSuspend(ReceipeMeasureRaw(
                receipeUuid, measureUuid, quantity
            ))
        }
    }

    fun updateReceipeName(newName: String) {
        viewModelScope.launch {
            val keyValue = rStringKeyValue.getSuspend(receipe.value!!.nameUuid, "fr")!!
            keyValue.value = newName
            rStringKeyValue.updateSuspend(keyValue)
        }
    }

    fun updateReceipePortions(newPortions: Int) {
        viewModelScope.launch {
            val r = receipe.value!!
            r.portions = newPortions
            rReceipe.updateSuspend(r)
        }
    }

    fun updateReceipeStars(newStars: Int) {
        viewModelScope.launch {
            val r = receipe.value!!
            r.stars = newStars
            rReceipe.updateSuspend(r)
        }
    }

    fun insertStep(description: String) {
        viewModelScope.launch {
            val sid = generateUuid()
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValueRaw(sid, "fr", description))
            rReceipeStep.insertSuspend(ReceipeStep(generateUuid(), receipe.value!!.uuid, null, sid, 0))
        }
    }
}