package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.repository.AlimentRepository

class AlimentAddSearchViewModel constructor(val repository: AlimentRepository) : ViewModel() {
    private val alimentSearchTerm: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val aliments = alimentSearchTerm.switchMap { repository.search(it) }

    fun searchAliments(name: String) {
        alimentSearchTerm.value = name
    }
}