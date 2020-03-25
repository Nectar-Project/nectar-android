package com.realitix.mealassistant.repository

import android.content.Context
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.*


class MeasureRepository(val context: Context): NameRepositoryInterface<MeasureRaw, MeasureNameRaw> {
    override fun insert(i: MeasureRaw) {
        return MealDatabase.getInstance(context).measureDao().insert(i)
    }

    override fun insertName(i: MeasureNameRaw) {
        return MealDatabase.getInstance(context).measureNameDao().insert(i)
    }

    override fun getRaw(uuid: String): MeasureRaw? {
        return MealDatabase.getInstance(context).measureDao().get(uuid)
    }

    companion object {
        private var instance: MeasureRepository? = null
        @Synchronized
        fun getInstance(context: Context): MeasureRepository {
            if (instance == null) {
                instance = MeasureRepository(context)
            }
            return instance!!
        }
    }
}