package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentStateRepository(val context: Context, updater: EntityUpdaterInterface<AlimentStateRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentStateRaw, AlimentState>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentStateDao()

    fun getUuid(uuid: String): AlimentState? = getDao().getUuid(uuid)

    class Updater(context: Context): GenericEntityUpdater<AlimentStateRaw>(context) {
        override fun newDatabaseUpdate(entity: AlimentStateRaw) = DatabaseUpdateRaw(
            entity.alimentUuid, EntityType.ALIMENT, NectarUtil.timestamp())
    }
}