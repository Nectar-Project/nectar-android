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
        override fun newDatabaseUpdate(entity: AlimentStateMeasureRaw): DatabaseUpdateRaw {
            return DatabaseUpdateRaw("dummy", EntityType.UNKNOW, 0)
        }

        override fun onEntityUpdate(entity: AlimentStateMeasureRaw) {
            DatabaseUpdateRepository(context).insert(
                DatabaseUpdateRaw(
                    AlimentStateRepository(context).getUuid(entity.alimentStateUuid)!!.alimentUuid,
                    EntityType.ALIMENT, NectarUtil.timestamp()
                )
            )
        }
        override suspend fun onEntityUpdateSuspend(entity: AlimentStateMeasureRaw) {
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(
                    AlimentStateRepository(context).getUuidSuspend(entity.alimentStateUuid)!!.alimentUuid,
                    EntityType.ALIMENT, NectarUtil.timestamp()
                )
            )
        }
    }
}