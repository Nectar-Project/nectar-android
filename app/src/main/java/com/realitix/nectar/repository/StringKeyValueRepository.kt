package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class StringKeyValueRepository(val context: Context, updater: EntityUpdaterInterface<StringKeyValueRaw> = Updater(context)):
    GenericGetJoinRepository<StringKeyValueRaw, StringKeyValue>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).stringKeyValueDao()

    class Updater(context: Context): GenericEntityUpdater<StringKeyValueRaw>(context) {
        override fun getUuidType(entity: StringKeyValueRaw): Pair<String, EntityType> = Pair(entity.stringKeyUuid, EntityType.STRING_KEY)
    }
}