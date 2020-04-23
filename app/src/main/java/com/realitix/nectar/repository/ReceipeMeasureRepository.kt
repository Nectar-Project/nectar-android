package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeMeasureRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeMeasureRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeMeasureRaw, ReceipeMeasure>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeMeasureDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeMeasureRaw>(context) {
        override fun newDatabaseUpdate(entity: ReceipeMeasureRaw) = DatabaseUpdateRaw(
            entity.receipeUuid, EntityType.RECEIPE, NectarUtil.timestamp())
    }
}