package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.StringKeyRaw
import com.realitix.nectar.database.entity.StringKeyValue
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.runBlocking

class ReceipeListViewModel (
    private val rReceipe: ReceipeRepository,
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository
): ViewModel() {
    val receipes: LiveData<List<Receipe>> = rReceipe.listLive()

    fun createReceipe(name: String): String {
        val rid = generateUuid()
        val sid = generateUuid()
        runBlocking {
            // create name
            rStringKey.insertSuspend(StringKeyRaw(sid))
            rStringKeyValue.insertSuspend(StringKeyValue(sid, "fr", name))

            // create receipe
            rReceipe.insertSuspend(Receipe(rid, sid, 2, 2))
        }
        return rid
    }
}