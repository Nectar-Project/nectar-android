package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.repository.ReceipeRepository

class ReceipeListViewModel constructor(repository: ReceipeRepository) : ViewModel() {
    val receipes: LiveData<List<Receipe>> = repository.getReceipes()
}