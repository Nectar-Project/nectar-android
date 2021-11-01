package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UpdateType

class ReceipeStepAlimentStateRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeStepAlimentStateRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeStepAlimentStateRaw, ReceipeStepAlimentState>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepAlimentDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeStepAlimentStateRaw>(context) {
        override suspend fun onEntityUpdateSuspend(entity: ReceipeStepAlimentStateRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.UPDATE, NectarUtil.timestamp()))
        }

        override suspend fun onEntityInsertSuspend(entity: ReceipeStepAlimentStateRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.INSERT, NectarUtil.timestamp()))
        }

        override suspend fun onEntityDeleteSuspend(entity: ReceipeStepAlimentStateRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.DELETE, NectarUtil.timestamp()))
        }

        override fun getUuidType(entity: ReceipeStepAlimentStateRaw): Pair<String, EntityType> = Pair(
            ReceipeStepRepository(context).get(entity.stepUuid)!!.receipeUuid, EntityType.RECEIPE
        )

        private suspend fun getUuidTypeSuspend(entity: ReceipeStepAlimentStateRaw): Pair<String, EntityType> = Pair(
            ReceipeStepRepository(context).getSuspend(entity.stepUuid)!!.receipeUuid, EntityType.RECEIPE
        )
    }

}