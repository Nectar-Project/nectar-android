package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeStepRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeStepRaw> = Updater(context)):
    GenericGetUuidRepository<ReceipeStepRaw, ReceipeStep>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeStepRaw>(context) {
        override fun newDatabaseUpdate(entity: ReceipeStepRaw) = DatabaseUpdateRaw(
            entity.receipeUuid, EntityType.RECEIPE, NectarUtil.timestamp())
    }

    fun listByReceipe(uuid: String): List<ReceipeStep> {
        return getDao().listByReceipe(uuid)
    }
}