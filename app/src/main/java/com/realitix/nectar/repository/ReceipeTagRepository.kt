package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeTagRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeTagRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeTagRaw, ReceipeTag>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeTagDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeTagRaw>(context) {
        override fun getUuidType(entity: ReceipeTagRaw): Pair<String, EntityType> = Pair(entity.receipeUuid, EntityType.RECEIPE)
    }
}