package com.realitix.mealassistant.repository

import android.content.Context
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.*


class UtensilRepository(val context: Context): NameRepositoryInterface<UtensilRaw, UtensilNameRaw> {
    override fun insert(i: UtensilRaw) {
        return MealDatabase.getInstance(context).utensilDao().insert(i)
    }

    override fun insertName(i: UtensilNameRaw) {
        return MealDatabase.getInstance(context).utensilNameDao().insert(i)
    }

    override fun getRaw(uuid: String): UtensilRaw? {
        return MealDatabase.getInstance(context).utensilDao().get(uuid)
    }

    companion object {
        private var instance: UtensilRepository? = null
        @Synchronized
        fun getInstance(context: Context): UtensilRepository {
            if (instance == null) {
                instance = UtensilRepository(context)
            }
            return instance!!
        }
    }
}