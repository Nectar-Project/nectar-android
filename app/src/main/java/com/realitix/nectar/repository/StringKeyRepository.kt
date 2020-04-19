package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class StringKeyRepository(val context: Context): GenericCrudRepository<StringKeyRaw, StringKey>() {
    override fun getDao() = NectarDatabase.getInstance(context).stringKeyDao()
}