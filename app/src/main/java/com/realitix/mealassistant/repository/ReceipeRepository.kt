package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.Receipe

class ReceipeRepository(val context: Context) {
    private val receipes: LiveData<List<Receipe>> = MealDatabase.getInstance(context).receipeDao().list()

    fun getReceipes(): LiveData<List<Receipe>> {
        return receipes
    }

    fun getReceipe(receipeId: Long): LiveData<Receipe> {
        return MealDatabase.getInstance(context).receipeDao().get(receipeId)
    }

    suspend fun hasReceipe(receipeId: Long): Boolean {
        if(MealDatabase.getInstance(context).receipeDao().has(receipeId) != null)
            return true
        return false
    }

    suspend fun createReceipe(receipe: Receipe): Long {
        return MealDatabase.getInstance(context).receipeDao().insert(receipe)
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