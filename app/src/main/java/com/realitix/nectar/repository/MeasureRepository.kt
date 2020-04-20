package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class MeasureRepository(val context: Context, updater: EntityUpdaterInterface<MeasureRaw> = Updater(context)):
    NameGenericRepository<MeasureRaw, Measure>(updater) {
    override fun getNameUuid(uuid: String) = get(uuid)!!.nameUuid
    override fun getDao() = NectarDatabase.getInstance(context).measureDao()

    class Updater(context: Context): GenericEntityUpdater<MeasureRaw>(context) {
        override fun newDatabaseUpdate(entity: MeasureRaw) = DatabaseUpdateRaw(
            entity.uuid, EntityType.MEASURE, NectarUtil.timestamp())
    }
}