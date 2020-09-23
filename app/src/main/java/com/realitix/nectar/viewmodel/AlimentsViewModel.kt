package com.realitix.nectar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AlimentsViewModel(
    private val rAliment: AlimentRepository,
    private val rState: StateRepository,
    private val rAlimentState: AlimentStateRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository
): ViewModel() {
    val aliments: LiveData<List<Aliment>> = rAliment.listLive()

    fun createAliment(name: String) {
        val aid = generateUuid()
        val sid = generateUuid()
        runBlocking {
            // create name
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValue(sid, "fr", name))

            // create aliment
            rAliment.insertSuspend(AlimentRaw(aid, sid))
        }
    }

    fun getAllStates(): List<State> {
        return runBlocking {
            rState.listSuspend()
        }
    }

    fun insertAlimentState(alimentUuid: String, stateUuid: String, fn: (alimentState: AlimentState) -> Unit) {
        viewModelScope.launch {
            val newUuid = generateUuid()
            rAlimentState.insertSuspend(
                AlimentStateRaw(
                    newUuid, alimentUuid,
                    stateUuid, Nutrition.generate()
                )
            )
            val newAlimentState = rAlimentState.getUuidSuspend(newUuid)!!
            fn(newAlimentState)
        }
    }

    fun insertState(name: String) {
        runBlocking {
            val sid = generateUuid()
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValueRaw(sid, "fr", name))
            rState.insertSuspend(StateRaw(generateUuid(), sid))
        }
    }

    fun deleteAliment(aliment: Aliment) {
        viewModelScope.launch {
            val alimentStates = rAlimentState.listByAlimentSuspend(aliment.uuid)
            for(a in alimentStates) {
                rAlimentState.deleteSuspend(a)
            }
            rAliment.deleteSuspend(aliment)
        }
    }

    fun updateAlimentName(aliment: Aliment, newName: String) {
        viewModelScope.launch {
            val keyValue = rStringKeyValue.getSuspend(aliment.nameUuid, "fr")!!
            keyValue.value = newName
            rStringKeyValue.updateSuspend(keyValue)
        }
    }
}