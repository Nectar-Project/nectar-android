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
    private val rReceipeTag: ReceipeTagRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository,
    private val rMeasure: MeasureRepository,
    private val rTag: TagRepository,
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

    fun getAllTags(): List<Tag> {
        return runBlocking {
            rTag.listSuspend()
        }
    }

    fun insertTag(name: String) {
        runBlocking {
            val sid = generateUuid()
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValueRaw(sid, "fr", name))
            rTag.insertSuspend(TagRaw(generateUuid(), sid))
        }
    }

    fun insertReceipeTag(tagUuid: String) {
        viewModelScope.launch {
            rReceipeTag.insertSuspend(ReceipeTagRaw(
                receipeUuid, tagUuid
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
            // Force receipe reload
            rReceipe.updateSuspend(receipe.value!!)
        }
    }

    fun deleteReceipe(
        rReceipeStepAlimentState: ReceipeStepAlimentStateRepository,
        rReceipeStepReceipe: ReceipeStepReceipeRepository
    ) {
        runBlocking {
            val r = rReceipe.getSuspend(receipeUuid)!!

            // Delete measure
            for(m in r.measures) {
                rReceipeMeasure.deleteSuspend(m)
            }

            // Delete tags
            for(t in  r.tags) {
                rReceipeTag.deleteSuspend(t)
            }

            // Delete steps
            for(step in r.getSteps(rReceipeStep)) {
                for(a in step.aliments) {
                    rReceipeStepAlimentState.deleteSuspend(a)
                }
                for(a in step.receipes) {
                    rReceipeStepReceipe.deleteSuspend(a)
                }
                rReceipeStep.deleteSuspend(step)
            }

            rReceipe.deleteSuspend(r)
        }
    }
}