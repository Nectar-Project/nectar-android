package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class AlimentRepository(val context: Context) {
    fun search(name: String): LiveData<List<Aliment>> {
        return NectarDatabase.getInstance(context).alimentDao().search(NectarUtil.searchMaker(name))
    }

    fun getAlimentLive(uuid: String): LiveData<Aliment> {
        return NectarDatabase.getInstance(context).alimentDao().getLive(uuid)
    }

    fun getAliment(uuid: String): Aliment? {
        return NectarDatabase.getInstance(context).alimentDao().get(uuid)
    }

    fun insertAliment(aliment: AlimentRaw) {
        return NectarDatabase.getInstance(context).alimentDao().insert(aliment)
    }

    fun insertAlimentName(alimentName: AlimentNameRaw) {
        return NectarDatabase.getInstance(context).alimentNameDao().insert(alimentName)
    }

    fun insertAlimentImage(a: AlimentImageRaw) {
        return NectarDatabase.getInstance(context).alimentImageDao().insert(a)
    }

    fun insertAlimentTag(alimentTag: AlimentTagRaw) {
        return NectarDatabase.getInstance(context).alimentTagDao().insert(alimentTag)
    }

    fun insertAlimentState(alimentState: AlimentStateRaw) {
        return NectarDatabase.getInstance(context).alimentStateDao().insert(alimentState)
    }

    fun insertAlimentStateMeasure(alimentStateMeasure: AlimentStateMeasureRaw) {
        return NectarDatabase.getInstance(context).alimentStateMeasureDao().insert(alimentStateMeasure)
    }
}