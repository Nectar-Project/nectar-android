package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentStateMeasureRepository(val context: Context, updater: EntityUpdaterInterface<AlimentStateMeasureRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentStateMeasureRaw, AlimentStateMeasure>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentStateMeasureDao()

    class Updater(context: Context): GenericEntityUpdater<AlimentStateMeasureRaw>(context) {
        override fun newDatabaseUpdate(entity: AlimentStateMeasureRaw) = DatabaseUpdateRaw(
                AlimentStateRepository(context).getUuid(entity.alimentStateUuid)!!.alimentUuid,
                EntityType.ALIMENT, NectarUtil.timestamp()
            )
    }
}