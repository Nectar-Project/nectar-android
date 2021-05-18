package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeStepReceipeRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeStepReceipeRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeStepReceipeRaw, ReceipeStepReceipe>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepReceipeDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeStepReceipeRaw>(context) {
        override fun newDatabaseUpdate(entity: ReceipeStepReceipeRaw) = DatabaseUpdateRaw(
            ReceipeStepRepository(context).get(entity.stepUuid)!!.receipeUuid,
            EntityType.RECEIPE, NectarUtil.timestamp())

        private suspend fun newDatabaseUpdateSuspend(entity: ReceipeStepReceipeRaw) = DatabaseUpdateRaw(
            ReceipeStepRepository(context).getSuspend(entity.stepUuid)!!.receipeUuid,
            EntityType.RECEIPE, NectarUtil.timestamp())

        override suspend fun onEntityUpdateSuspend(entity: ReceipeStepReceipeRaw) {
            DatabaseUpdateRepository(context).insertSuspend(newDatabaseUpdateSuspend(entity))
        }
    }
}