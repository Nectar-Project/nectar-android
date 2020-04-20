package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.DatabaseUpdate
import com.realitix.nectar.database.entity.DatabaseUpdateRaw

class DatabaseUpdateRepository(val context: Context, updater: EntityUpdaterInterface<DatabaseUpdateRaw> = NoTrackEntityUpdater()):
    GenericCrudRepository<DatabaseUpdateRaw, DatabaseUpdate>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).databaseUpdateDao()
}