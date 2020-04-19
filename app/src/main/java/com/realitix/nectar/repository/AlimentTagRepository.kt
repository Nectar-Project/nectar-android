package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentTagRepository(val context: Context, updater: EntityUpdaterInterface<AlimentTagRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentTagRaw, AlimentTag>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentTagDao()

    class Updater(val context: Context): EntityUpdaterInterface<AlimentTagRaw> {
        override fun onEntityUpdate(entity: AlimentTagRaw) {
            NectarDatabase.getInstance(context).databaseUpdateDao().insert(
                DatabaseUpdateRaw(entity.alimentUuid, EntityType.ALIMENT, NectarUtil.timestamp())
            )
        }
    }
}