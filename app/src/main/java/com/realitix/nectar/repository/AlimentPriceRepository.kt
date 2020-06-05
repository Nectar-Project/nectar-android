package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentPriceRepository(val context: Context, updater: EntityUpdaterInterface<AlimentPriceRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentPriceRaw, AlimentPrice>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentPriceDao()

    class Updater(context: Context): GenericEntityUpdater<AlimentPriceRaw>(context) {
        override fun newDatabaseUpdate(entity: AlimentPriceRaw) = DatabaseUpdateRaw(
            entity.alimentUuid, EntityType.ALIMENT, NectarUtil.timestamp())
    }
}