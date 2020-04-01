package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeName
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.runBlocking

class ReceipeListViewModel constructor(val repository: ReceipeRepository) : ViewModel() {
    val receipes: LiveData<List<Receipe>> = repository.getReceipes()

    fun createReceipe(): String {
        val rid = generateUuid()
        runBlocking {
            val newReceipe = Receipe(rid, 2, 2)
            val newReceipeName = ReceipeName(rid, "fr", "Nouvelle recette")
            repository.createReceipe(newReceipe)
            repository.createReceipeName(newReceipeName)
        }
        return rid
    }
}