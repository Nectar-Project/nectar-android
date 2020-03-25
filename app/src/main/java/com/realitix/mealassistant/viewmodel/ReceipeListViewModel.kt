package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeName
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.MealUtil.Companion.generateUuid
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