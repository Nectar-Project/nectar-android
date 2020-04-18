package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.*


class StringKeyValueRepository(val context: Context): GenericRepository<StringKeyValueRaw, StringKeyValue>() {
    override fun getDao() = NectarDatabase.getInstance(context).stringKeyValueDao()
    fun getLang(uuid: String, lang: String) = getDao().getLang(uuid, lang)
}