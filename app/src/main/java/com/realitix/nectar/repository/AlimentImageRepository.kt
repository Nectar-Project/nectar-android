package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentImageRepository(val context: Context, updater: EntityUpdaterInterface<AlimentImageRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentImageRaw, AlimentImage>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentImageDao()

    class Updater(context: Context): GenericEntityUpdater<AlimentImageRaw>(context) {
        override fun newDatabaseUpdate(entity: AlimentImageRaw) = DatabaseUpdateRaw(
            entity.alimentUuid, EntityType.ALIMENT, NectarUtil.timestamp())
    }
}