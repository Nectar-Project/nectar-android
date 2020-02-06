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

class ReceipeStepViewModel constructor(var repository: ReceipeRepository, receipeId: Long, stepId: Long) : ViewModel() {
    val receipe: LiveData<Receipe> = repository.getReceipe(receipeId)
    val step: LiveData<ReceipeStepDao.ReceipeStepFull> = {
        var rid = stepId
        runBlocking {
            if(!repository.hasReceipeStep(rid)) {
                val r = ReceipeStep(receipeId, 0, "Nouvelle étape", 0)
                rid = repository.createReceipeStep(r)
            }
        }
        repository.getReceipeStepFull(rid)
    }()
}