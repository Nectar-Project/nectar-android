package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.StringKeyRaw
import com.realitix.nectar.database.entity.StringKeyValue
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.runBlocking

class ReceipeListViewModel constructor(
    private val receipeRepository: ReceipeRepository,
    private val stringKeyRepository: StringKeyRepository
) : ViewModel() {
    val receipes: LiveData<List<Receipe>> = receipeRepository.getReceipes()

    fun createReceipe(): String {
        val rid = generateUuid()
        val sid = generateUuid()
        runBlocking {
            // create name
            stringKeyRepository.insertSuspend(StringKeyRaw(sid))
            stringKeyRepository.insertValueSuspend(StringKeyValue(sid, "fr", "Nouvelle recette"))

            // create receipe
            receipeRepository.createReceipeSuspend(Receipe(rid, sid, 2, 2))
        }
        return rid
    }
}