package com.realitix.nectar.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.DatabaseUpdate
import com.realitix.nectar.database.entity.DatabaseUpdateRaw

class DatabaseUpdateRepository(val context: Context, updater: EntityUpdaterInterface<DatabaseUpdateRaw> = NoTrackEntityUpdater()):
    GenericCrudRepository<DatabaseUpdateRaw, DatabaseUpdate>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).databaseUpdateDao()

    // insert
    override fun insert(i: DatabaseUpdateRaw) {
        try {
            getDao().insert(i)
        }
        catch (e: SQLiteConstraintException) {
            // Do nothing
        }
    }

    override suspend fun insertSuspend(i: DatabaseUpdateRaw) {
        try {
            getDao().insertSuspend(i)
        }
        catch (e: SQLiteConstraintException) {
            // Do nothing
        }
    }
}