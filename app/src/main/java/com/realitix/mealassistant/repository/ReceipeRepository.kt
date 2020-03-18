package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.database.entity.ReceipeStepAliment
import com.realitix.mealassistant.database.entity.ReceipeStepReceipe

class ReceipeRepository(val context: Context) {
    fun getReceipes(): LiveData<List<Receipe>> {
        return MealDatabase.getInstance(context).receipeDao().list()
    }

    fun search(name: String): LiveData<List<Receipe>> {
        return MealDatabase.getInstance(context).receipeDao().search(name)
    }

    fun getReceipe(receipeUuid: String): LiveData<Receipe> {
        return MealDatabase.getInstance(context).receipeDao().get(receipeUuid)
    }

    fun getReceipeFull(receipeUuid: String): LiveData<Receipe> {
        return MealDatabase.getInstance(context).receipeDao().getFull(receipeUuid)
    }

    fun getReceipeStepFull(stepUuid: String): LiveData<ReceipeStep> {
        return MealDatabase.getInstance(context).receipeStepDao().getFull(stepUuid)
    }

    suspend fun update(receipe: Receipe) {
        MealDatabase.getInstance(context).receipeDao().update(receipe)
    }

    suspend fun hasReceipe(receipeUuid: String): Boolean {
        if(MealDatabase.getInstance(context).receipeDao().has(receipeUuid) != null)
            return true
        return false
    }

    suspend fun hasReceipeStep(stepUuid: String): Boolean {
        if(MealDatabase.getInstance(context).receipeStepDao().has(stepUuid) != null)
            return true
        return false
    }

    suspend fun createReceipe(receipe: Receipe): Long {
        return MealDatabase.getInstance(context).receipeDao().insert(receipe)
    }

    suspend fun createReceipeStep(receipeStep: ReceipeStep): Long {
        return MealDatabase.getInstance(context).receipeStepDao().insert(receipeStep)
    }

    suspend fun createReceipeStepAliment(receipeStepAliment: ReceipeStepAliment): Long {
        return MealDatabase.getInstance(context).receipeStepAlimentDao().insert(receipeStepAliment)
    }

    suspend fun createReceipeStepReceipe(receipeStepReceipe: ReceipeStepReceipe): Long {
        return MealDatabase.getInstance(context).receipeStepReceipeDao().insert(receipeStepReceipe)
    }

    companion object {
        private var instance: ReceipeRepository? = null
        @Synchronized
        fun getInstance(context: Context): ReceipeRepository {
            if (instance == null) {
                instance = ReceipeRepository(context)
            }
            return instance as ReceipeRepository
        }
    }
}