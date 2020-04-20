package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.dao.GenericCrudDao
import com.realitix.nectar.database.entity.DatabaseUpdateRaw

abstract class GenericCrudRepository<ERaw, E>(private val entityUpdaterListener: EntityUpdaterInterface<ERaw>) {
    abstract fun getDao(): GenericCrudDao<ERaw, E>

    interface EntityUpdaterInterface<ERaw> {
        fun onEntityUpdate(entity: ERaw)
        suspend fun onEntityUpdateSuspend(entity: ERaw)
    }

    abstract class GenericEntityUpdater<ERaw>(val context: Context): EntityUpdaterInterface<ERaw> {
        abstract fun newDatabaseUpdate(entity: ERaw): DatabaseUpdateRaw

        override fun onEntityUpdate(entity: ERaw) {
            DatabaseUpdateRepository(context).insert(newDatabaseUpdate(entity))
        }
        override suspend fun onEntityUpdateSuspend(entity: ERaw) {
            DatabaseUpdateRepository(context).insertSuspend(newDatabaseUpdate(entity))
        }
    }

    class NoTrackEntityUpdater<ERaw>: EntityUpdaterInterface<ERaw> {
        override fun onEntityUpdate(entity: ERaw) {}
        override suspend fun onEntityUpdateSuspend(entity: ERaw) {}
    }

    // list
    fun list() = getDao().list()
    suspend fun listSuspend() = getDao().listSuspend()
    fun listLive() = getDao().listLive()

    // insert
    open fun insert(i: ERaw) {
        getDao().insert(i)
        entityUpdaterListener.onEntityUpdate(i)
    }

    open suspend fun insertSuspend(i: ERaw) {
        getDao().insertSuspend(i)
        entityUpdaterListener.onEntityUpdateSuspend(i)
    }

    // update
    open fun update(i: ERaw) {
        getDao().update(i)
        entityUpdaterListener.onEntityUpdate(i)
    }

    open suspend fun updateSuspend(i: ERaw) {
        getDao().updateSuspend(i)
        entityUpdaterListener.onEntityUpdateSuspend(i)
    }

    // delete
    open fun delete(i: ERaw) {
        getDao().delete(i)
        entityUpdaterListener.onEntityUpdate(i)
    }

    open suspend fun deleteSuspend(i: ERaw) {
        getDao().deleteSuspend(i)
        entityUpdaterListener.onEntityUpdateSuspend(i)
    }
}