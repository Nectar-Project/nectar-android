package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.repository.ReceipeRepository

class ReceipeViewModel constructor(repository: ReceipeRepository, receipeId: Long) : ViewModel() {
    val receipe: LiveData<Receipe> = repository.getReceipe(receipeId)
}