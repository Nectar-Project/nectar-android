package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeStepAlimentRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeStepAlimentRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeStepAlimentRaw, ReceipeStepAliment>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepAlimentDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeStepAlimentRaw>(context) {
        override fun newDatabaseUpdate(entity: ReceipeStepAlimentRaw) = DatabaseUpdateRaw(
            ReceipeStepRepository(context).get(entity.stepUuid)!!.receipeUuid,
            EntityType.RECEIPE, NectarUtil.timestamp())
    }
}