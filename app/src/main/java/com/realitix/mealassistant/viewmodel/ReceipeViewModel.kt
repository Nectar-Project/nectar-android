package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.repository.ReceipeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReceipeViewModel constructor(repository: ReceipeRepository, receipeId: Long) : ViewModel() {
    //val receipe: LiveData<Receipe> = repository.getReceipe(receipeId)
    val receipe: LiveData<Receipe> = {
        var rid = receipeId
        runBlocking {
            if(!repository.hasReceipe(rid)) {
                val r = Receipe("Nouvelle recette", 2, 2)
                rid = repository.createReceipe(r)
            }
        }
        repository.getReceipe(rid)
    }()
}