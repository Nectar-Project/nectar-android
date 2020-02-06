package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.repository.ReceipeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReceipeViewModel constructor(val repository: ReceipeRepository, receipeId: Long) : ViewModel() {
    val receipe: LiveData<ReceipeDao.ReceipeFull> = {
        var rid = receipeId
        runBlocking {
            if(!repository.hasReceipe(rid)) {
                val r = Receipe("Nouvelle recette", 2, 2)
                rid = repository.createReceipe(r)
            }
        }
        repository.getReceipeFull(rid)
    }()

    fun updateReceipeName(newName: String) {
        val r = receipe.value!!
        r.name = newName
        GlobalScope.launch {
            repository.update(r)
        }
    }

    fun createStep(description: String) {
        val step = ReceipeStep(receipe.value!!.id, 0, description, 0)
        GlobalScope.launch {
            repository.createReceipeStep(step)
        }
    }
}