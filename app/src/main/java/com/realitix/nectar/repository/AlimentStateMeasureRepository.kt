package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UpdateType

class AlimentStateMeasureRepository(val context: Context, updater: EntityUpdaterInterface<AlimentStateMeasureRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentStateMeasureRaw, AlimentStateMeasure>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentStateMeasureDao()

    class Updater(context: Context): GenericEntityUpdater<AlimentStateMeasureRaw>(context) {
        override suspend fun onEntityUpdateSuspend(entity: AlimentStateMeasureRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.UPDATE, NectarUtil.timestamp()))
        }

        override suspend fun onEntityInsertSuspend(entity: AlimentStateMeasureRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.INSERT, NectarUtil.timestamp()))
        }

        override suspend fun onEntityDeleteSuspend(entity: AlimentStateMeasureRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.DELETE, NectarUtil.timestamp()))
        }

        override fun getUuidType(entity: AlimentStateMeasureRaw): Pair<String, EntityType> = Pair(
            AlimentStateRepository(context).getUuid(entity.alimentStateUuid)!!.alimentUuid,
            EntityType.ALIMENT
        )

        private suspend fun getUuidTypeSuspend(entity: AlimentStateMeasureRaw): Pair<String, EntityType> = Pair(
            AlimentStateRepository(context).getUuidSuspend(entity.alimentStateUuid)!!.alimentUuid,
            EntityType.ALIMENT
        )
    }
}