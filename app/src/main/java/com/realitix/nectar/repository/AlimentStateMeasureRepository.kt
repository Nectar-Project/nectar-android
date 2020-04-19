package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentStateMeasureRepository(val context: Context, updater: EntityUpdaterInterface<AlimentStateMeasureRaw> = Updater(context)):
    GenericCrudRepository<AlimentStateMeasureRaw, AlimentStateMeasure>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentStateMeasureDao()

    class Updater(val context: Context): EntityUpdaterInterface<AlimentStateMeasureRaw> {
        override fun onEntityUpdate(entity: AlimentStateMeasureRaw) {
            NectarDatabase.getInstance(context).databaseUpdateDao().insert(
                DatabaseUpdateRaw(
                    AlimentStateRepository(context).get(entity.alimentStateUuid)!!.alimentUuid,
                    EntityType.ALIMENT, NectarUtil.timestamp()
                )
            )
        }
    }
}