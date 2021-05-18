package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeStepAlimentStateRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeStepAlimentStateRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeStepAlimentStateRaw, ReceipeStepAlimentState>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepAlimentDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeStepAlimentStateRaw>(context) {
        override fun newDatabaseUpdate(entity: ReceipeStepAlimentStateRaw) = DatabaseUpdateRaw(
            ReceipeStepRepository(context).get(entity.stepUuid)!!.receipeUuid,
            EntityType.RECEIPE, NectarUtil.timestamp())

        private suspend fun newDatabaseUpdateSuspend(entity: ReceipeStepAlimentStateRaw) = DatabaseUpdateRaw(
            ReceipeStepRepository(context).getSuspend(entity.stepUuid)!!.receipeUuid,
            EntityType.RECEIPE, NectarUtil.timestamp())

        override suspend fun onEntityUpdateSuspend(entity: ReceipeStepAlimentStateRaw) {
            DatabaseUpdateRepository(context).insertSuspend(newDatabaseUpdateSuspend(entity))
        }
    }

}