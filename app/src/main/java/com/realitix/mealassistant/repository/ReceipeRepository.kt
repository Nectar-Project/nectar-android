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