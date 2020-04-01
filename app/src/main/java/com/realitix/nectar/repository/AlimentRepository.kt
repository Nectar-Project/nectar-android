package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.MealDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.MealUtil

class AlimentRepository(val context: Context) {
    fun search(name: String): LiveData<List<Aliment>> {
        return MealDatabase.getInstance(context).alimentDao().search(MealUtil.searchMaker(name))
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

    fun insertAlimentImage(a: AlimentImageRaw) {
        return MealDatabase.getInstance(context).alimentImageDao().insert(a)
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
}