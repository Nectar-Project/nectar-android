package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class MeasureRepository(val context: Context): NameRepositoryInterface<MeasureRaw, MeasureNameRaw> {
    override fun insert(i: MeasureRaw) {
        return NectarDatabase.getInstance(context).measureDao().insert(i)
    }

    override fun insertName(i: MeasureNameRaw) {
        return NectarDatabase.getInstance(context).measureNameDao().insert(i)
    }

    override fun getRaw(uuid: String): MeasureRaw? {
        return NectarDatabase.getInstance(context).measureDao().get(uuid)
    }

    override fun getNamesMap(uuid: String): Map<String, String> {
        val measure = NectarDatabase.getInstance(context).measureDao().get(uuid)!!
        val result = mutableMapOf<String, String>()
        for(n in measure.names) {
            result[n.language] = n.name
        }
        return result
    }
}