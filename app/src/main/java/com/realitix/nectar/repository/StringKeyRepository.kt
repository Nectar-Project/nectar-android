package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class StringKeyRepository(val context: Context, updater: EntityUpdaterInterface<StringKeyRaw> = Updater(context)):
    GenericGetUuidRepository<StringKeyRaw, StringKey>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).stringKeyDao()

    class Updater(context: Context): GenericEntityUpdater<StringKeyRaw>(context) {
        override fun getUuidType(entity: StringKeyRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.STRING_KEY)
    }
}