package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UpdateType

class ReceipeStepReceipeRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeStepReceipeRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeStepReceipeRaw, ReceipeStepReceipe>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepReceipeDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeStepReceipeRaw>(context) {
        override suspend fun onEntityUpdateSuspend(entity: ReceipeStepReceipeRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.UPDATE, NectarUtil.timestamp()))
        }

        override suspend fun onEntityInsertSuspend(entity: ReceipeStepReceipeRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.INSERT, NectarUtil.timestamp()))
        }

        override suspend fun onEntityDeleteSuspend(entity: ReceipeStepReceipeRaw) {
            val (uuid, etype) = getUuidTypeSuspend(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.DELETE, NectarUtil.timestamp()))
        }

        override fun getUuidType(entity: ReceipeStepReceipeRaw): Pair<String, EntityType> = Pair(
            ReceipeStepRepository(context).get(entity.stepUuid)!!.receipeUuid,
            EntityType.RECEIPE
        )

        private suspend fun getUuidTypeSuspend(entity: ReceipeStepReceipeRaw): Pair<String, EntityType> = Pair(
            ReceipeStepRepository(context).getSuspend(entity.stepUuid)!!.receipeUuid,
            EntityType.RECEIPE
        )
    }
}