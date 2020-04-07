package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class StringKeyRepository(val context: Context) {
    fun insert(i: StringKeyRaw) {
        return NectarDatabase.getInstance(context).nameDao().insert(i)
    }

    suspend fun insertSuspend(i: StringKeyRaw) {
        return NectarDatabase.getInstance(context).nameDao().insertSuspend(i)
    }

    fun getValue(nameUuid: String, lang: String): StringKeyValue? {
        return NectarDatabase.getInstance(context).nameValueDao().get(nameUuid, lang)
    }

    suspend fun getValueSuspend(nameUuid: String, lang: String): StringKeyValue? {
        return NectarDatabase.getInstance(context).nameValueDao().getSuspend(nameUuid, lang)
    }

    fun insertValue(i: StringKeyValueRaw) {
        return NectarDatabase.getInstance(context).nameValueDao().insert(i)
    }

    suspend fun insertValueSuspend(i: StringKeyValueRaw) {
        return NectarDatabase.getInstance(context).nameValueDao().insertSuspend(i)
    }

    fun updateValue(i: StringKeyValueRaw) {
        return NectarDatabase.getInstance(context).nameValueDao().update(i)
    }

    suspend fun updateValueSuspend(i: StringKeyValueRaw) {
        return NectarDatabase.getInstance(context).nameValueDao().updateSuspend(i)
    }

    fun get(uuid: String): StringKey? {
        return NectarDatabase.getInstance(context).nameDao().get(uuid)
    }
}