package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.*

class AlimentRepository(val context: Context) {

    fun search(name: String): LiveData<List<Aliment>> {
        return MealDatabase.getInstance(context).alimentDao().search(name)
    }

    fun getAlimentLive(uuid: String): LiveData<Aliment> {
        return MealDatabase.getInstance(context).alimentDao().getLive(uuid)
    }

    fun getAliment(uuid: String): Aliment? {
        return MealDatabase.getInstance(context).alimentDao().get(uuid)
    }

    fun insertAliment(aliment: AlimentRaw) {
        return MealDatabase.getInstance(context).alimentDao().insert(aliment)
    }

    fun insertAlimentName(alimentName: AlimentNameRaw) {
        return MealDatabase.getInstance(context).alimentNameDao().insert(alimentName)
    }

    fun insertAlimentTag(alimentTag: AlimentTagRaw) {
        return MealDatabase.getInstance(context).alimentTagDao().insert(alimentTag)
    }

    fun insertAlimentState(alimentState: AlimentStateRaw) {
        return MealDatabase.getInstance(context).alimentStateDao().insert(alimentState)
    }

    fun insertAlimentStateMeasure(alimentStateMeasure: AlimentStateMeasureRaw) {
        return MealDatabase.getInstance(context).alimentStateMeasureDao().insert(alimentStateMeasure)
    }

    companion object {
        private var instance: AlimentRepository? = null
        @Synchronized
        fun getInstance(context: Context): AlimentRepository {
            if (instance == null) {
                instance = AlimentRepository(context)
            }
            return instance!!
        }
    }
}