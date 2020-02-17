package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.dao.ReceipeDao
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep

class AlimentRepository(val context: Context) {

    fun search(name: String): LiveData<List<Aliment>> {
        return MealDatabase.getInstance(context).alimentDao().search(name)
    }

    companion object {
        private var instance: AlimentRepository? = null
        @Synchronized
        fun getInstance(context: Context): AlimentRepository {
            if (instance == null) {
                instance = AlimentRepository(context)
            }
            return instance as AlimentRepository
        }
    }
}