package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep

class ReceipeRepository(val context: Context) {
    private val receipes: LiveData<List<Receipe>> = MealDatabase.getInstance(context).receipeDao().list()

    fun getReceipes(): LiveData<List<Receipe>> {
        return receipes
    }

    fun getReceipe(receipeId: Long): LiveData<Receipe> {
        return MealDatabase.getInstance(context).receipeDao().get(receipeId)
    }

    fun getReceipeFull(receipeId: Long): LiveData<ReceipeDao.ReceipeFull> {
        return MealDatabase.getInstance(context).receipeDao().getFull(receipeId)
    }

    fun getReceipeStepFull(stepId: Long): LiveData<ReceipeStepDao.ReceipeStepFull> {
        return MealDatabase.getInstance(context).receipeStepDao().getFull(stepId)
    }

    suspend fun update(receipe: Receipe) {
        MealDatabase.getInstance(context).receipeDao().update(receipe)
    }

    suspend fun hasReceipe(receipeId: Long): Boolean {
        if(MealDatabase.getInstance(context).receipeDao().has(receipeId) != null)
            return true
        return false
    }

    suspend fun hasReceipeStep(stepId: Long): Boolean {
        if(MealDatabase.getInstance(context).receipeStepDao().has(stepId) != null)
            return true
        return false
    }

    suspend fun createReceipe(receipe: Receipe): Long {
        return MealDatabase.getInstance(context).receipeDao().insert(receipe)
    }

    suspend fun createReceipeStep(receipeStep: ReceipeStep): Long {
        return MealDatabase.getInstance(context).receipeStepDao().insert(receipeStep)
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