package com.realitix.nectar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AlimentViewModel(
    rAliment: AlimentRepository,
    private val rState: StateRepository,
    private val rAlimentState: AlimentStateRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository,
    private val alimentUuid: String
): ViewModel() {
    val aliment: LiveData<Aliment> = rAliment.getLive(alimentUuid)

    fun getAllStates(): List<State> {
        return runBlocking {
            rState.listSuspend()
        }
    }

    fun insertAlimentState(stateUuid: String) {
        viewModelScope.launch {
            rAlimentState.insertSuspend(AlimentStateRaw(
                generateUuid(), alimentUuid,
                stateUuid, Nutrition.generate()
            ))
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
}