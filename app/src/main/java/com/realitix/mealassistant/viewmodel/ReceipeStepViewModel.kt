package com.realitix.mealassistant.viewmodel

import androidx.lifecycle.*
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.repository.ReceipeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReceipeStepViewModel constructor(var repository: ReceipeRepository, receipeUuid: String, stepUuid: String) : ViewModel() {
    val receipe: LiveData<Receipe> = repository.getReceipe(receipeUuid)
    val step: LiveData<ReceipeStep> = repository.getReceipeStepFull(stepUuid)
}