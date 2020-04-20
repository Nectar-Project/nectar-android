package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ImageRepository(val context: Context, updater: EntityUpdaterInterface<ImageRaw> = Updater(context)):
    GenericGetUuidRepository<ImageRaw, Image>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).imageDao()

    class Updater(context: Context): GenericEntityUpdater<ImageRaw>(context) {
        override fun newDatabaseUpdate(entity: ImageRaw) = DatabaseUpdateRaw(
            entity.uuid, EntityType.IMAGE, NectarUtil.timestamp())
    }
}