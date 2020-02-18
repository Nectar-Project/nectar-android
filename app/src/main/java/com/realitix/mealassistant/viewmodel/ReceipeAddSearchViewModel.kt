package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.ReceipeStepReceipe
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReceipeAddSearchViewModel constructor(val repository: ReceipeRepository, val stepId: Long) : ViewModel() {
    val receipeSearchTerm: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val receipes = receipeSearchTerm.switchMap { repository.search(it) }

    fun searchReceipes(name: String) {
        receipeSearchTerm.value = name
    }

    fun createReceipeStepReceipe(linkedReceipeId: Long) {
        val c = ReceipeStepReceipe(linkedReceipeId, stepId)
        GlobalScope.launch {
            repository.createReceipeStepReceipe(c)
        }
    }
}