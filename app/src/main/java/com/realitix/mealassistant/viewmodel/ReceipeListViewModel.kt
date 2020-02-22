package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.repository.ReceipeRepository
import kotlinx.coroutines.runBlocking

class ReceipeListViewModel constructor(val repository: ReceipeRepository) : ViewModel() {
    val receipes: LiveData<List<Receipe>> = repository.getReceipes()

    fun createReceipe(): Long {
        var rid: Long = -1
        runBlocking {
            val r = Receipe("Nouvelle recette", 2, 2)
            rid = repository.createReceipe(r)
        }
        return rid
    }
}