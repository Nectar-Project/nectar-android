package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class NameRepository(val context: Context) {
    fun insert(i: StringKeyRaw) {
        return NectarDatabase.getInstance(context).nameDao().insert(i)
    }

    fun getValue(nameUuid: String, lang: String): StringKeyValue? {
        return NectarDatabase.getInstance(context).nameValueDao().get(nameUuid, lang)
    }

    fun insertValue(i: StringKeyValueRaw) {
        return NectarDatabase.getInstance(context).nameValueDao().insert(i)
    }

    fun updateValue(i: StringKeyValueRaw) {
        return NectarDatabase.getInstance(context).nameValueDao().update(i)
    }

    fun get(uuid: String): StringKey? {
        return NectarDatabase.getInstance(context).nameDao().get(uuid)
    }
}