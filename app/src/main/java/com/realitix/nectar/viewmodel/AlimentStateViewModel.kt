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

class AlimentStateViewModel(
    rAlimentState: AlimentStateRepository,
    private val rAlimentStateMeasure: AlimentStateMeasureRepository,
    private val rMeasure: MeasureRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository,
    private val alimentStateUuid: String
): ViewModel() {
    val alimentState: LiveData<AlimentState> = rAlimentState.getUuidLive(alimentStateUuid)

    fun getAllMeasures(): List<Measure> {
        return runBlocking {
            rMeasure.listSuspend()
        }
    }

    fun insertAlimentStateMeasure(measureUuid: String, quantity: Int) {
        viewModelScope.launch {
            rAlimentStateMeasure.insertSuspend(AlimentStateMeasureRaw(
                alimentStateUuid, measureUuid, quantity
            ))
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
}