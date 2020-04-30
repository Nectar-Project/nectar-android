package com.realitix.nectar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentRaw
import com.realitix.nectar.database.entity.StringKeyRaw
import com.realitix.nectar.database.entity.StringKeyValue
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.runBlocking

class AlimentsViewModel(
    private val rAliment: AlimentRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository
): ViewModel() {
    val aliments: LiveData<List<Aliment>> = rAliment.listLive()

    fun createAliment(name: String): String {
        val aid = generateUuid()
        val sid = generateUuid()
        runBlocking {
            // create name
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValue(sid, "fr", name))

            // create aliment
            rAliment.insertSuspend(AlimentRaw(aid, sid))
        }
        return aid
    }
}