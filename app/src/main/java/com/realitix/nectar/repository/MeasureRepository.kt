package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.*


class MeasureRepository(val context: Context): NameGenericRepository<MeasureRaw, Measure>() {
    override fun getNameUuid(uuid: String) = get(uuid)!!.nameUuid
    override fun getDao() = NectarDatabase.getInstance(context).measureDao()
}