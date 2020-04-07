package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class MeasureRepository(val context: Context): NameRepositoryInterface<MeasureRaw> {
    override fun insert(i: MeasureRaw) {
        return NectarDatabase.getInstance(context).measureDao().insert(i)
    }

    override fun getRaw(uuid: String): MeasureRaw? {
        return NectarDatabase.getInstance(context).measureDao().get(uuid)
    }

    override fun getNameUuid(uuid: String): String {
        return getRaw(uuid)!!.nameUuid
    }
}