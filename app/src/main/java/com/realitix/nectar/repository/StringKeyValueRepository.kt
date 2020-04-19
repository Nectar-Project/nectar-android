package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class StringKeyValueRepository(val context: Context): GenericCrudRepository<StringKeyValueRaw, StringKeyValue>() {
    override fun getDao() = NectarDatabase.getInstance(context).stringKeyValueDao()
    fun getLang(uuid: String, lang: String) = getDao().getLang(uuid, lang)
    suspend fun getLangSuspend(uuid: String, lang: String) = getDao().getLangSuspend(uuid, lang)
}