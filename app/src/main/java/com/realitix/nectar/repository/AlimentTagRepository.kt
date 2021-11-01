package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentTagRepository(val context: Context, updater: EntityUpdaterInterface<AlimentTagRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentTagRaw, AlimentTag>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentTagDao()

    class Updater(context: Context): GenericEntityUpdater<AlimentTagRaw>(context) {
        override fun getUuidType(entity: AlimentTagRaw): Pair<String, EntityType> = Pair(entity.alimentUuid, EntityType.ALIMENT)
    }
}