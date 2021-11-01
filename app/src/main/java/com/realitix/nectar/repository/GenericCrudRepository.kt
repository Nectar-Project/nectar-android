package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.dao.GenericCrudDao
import com.realitix.nectar.database.entity.DatabaseUpdateRaw
import com.realitix.nectar.database.entity.MealReceipeRaw
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UpdateType

abstract class GenericCrudRepository<ERaw, E>(private val entityUpdaterListener: EntityUpdaterInterface<ERaw>) {
    abstract fun getDao(): GenericCrudDao<ERaw, E>

    interface EntityUpdaterInterface<ERaw> {
        fun onEntityUpdate(entity: ERaw)
        suspend fun onEntityUpdateSuspend(entity: ERaw)
        fun onEntityInsert(entity: ERaw)
        suspend fun onEntityInsertSuspend(entity: ERaw)
        fun onEntityDelete(entity: ERaw)
        suspend fun onEntityDeleteSuspend(entity: ERaw)
    }

    abstract class GenericEntityUpdater<ERaw>(val context: Context): EntityUpdaterInterface<ERaw> {
        abstract fun getUuidType(entity: ERaw): Pair<String, EntityType>

        override fun onEntityUpdate(entity: ERaw) {
            val (uuid, etype) = getUuidType(entity)
            DatabaseUpdateRepository(context).insert(
                DatabaseUpdateRaw(uuid, etype, UpdateType.UPDATE, NectarUtil.timestamp()))
        }
        override suspend fun onEntityUpdateSuspend(entity: ERaw) {
            val (uuid, etype) = getUuidType(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.UPDATE, NectarUtil.timestamp()))
        }

        override fun onEntityInsert(entity: ERaw) {
            val (uuid, etype) = getUuidType(entity)
            DatabaseUpdateRepository(context).insert(
                DatabaseUpdateRaw(uuid, etype, UpdateType.INSERT, NectarUtil.timestamp()))
        }

        override suspend fun onEntityInsertSuspend(entity: ERaw) {
            val (uuid, etype) = getUuidType(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.INSERT, NectarUtil.timestamp()))
        }

        override fun onEntityDelete(entity: ERaw) {
            val (uuid, etype) = getUuidType(entity)
            DatabaseUpdateRepository(context).insert(
                DatabaseUpdateRaw(uuid, etype, UpdateType.DELETE, NectarUtil.timestamp()))
        }

        override suspend fun onEntityDeleteSuspend(entity: ERaw) {
            val (uuid, etype) = getUuidType(entity)
            DatabaseUpdateRepository(context).insertSuspend(
                DatabaseUpdateRaw(uuid, etype, UpdateType.DELETE, NectarUtil.timestamp()))
        }
    }

    class NoTrackEntityUpdater<ERaw>: EntityUpdaterInterface<ERaw> {
        override fun onEntityUpdate(entity: ERaw) {}
        override suspend fun onEntityUpdateSuspend(entity: ERaw) {}
        override fun onEntityInsert(entity: ERaw) {}
        override suspend fun onEntityInsertSuspend(entity: ERaw) {}
        override fun onEntityDelete(entity: ERaw) {}
        override suspend fun onEntityDeleteSuspend(entity: ERaw) {}
    }

    // list
    fun list() = getDao().list()
    suspend fun listSuspend() = getDao().listSuspend()
    fun listLive() = getDao().listLive()

    // insert
    open fun insert(i: ERaw) {
        getDao().insert(i)
        entityUpdaterListener.onEntityInsert(i)
    }

    open suspend fun insertSuspend(i: ERaw) {
        getDao().insertSuspend(i)
        entityUpdaterListener.onEntityInsertSuspend(i)
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
        entityUpdaterListener.onEntityDelete(i)
    }

    open suspend fun deleteSuspend(i: ERaw) {
        getDao().deleteSuspend(i)
        entityUpdaterListener.onEntityDeleteSuspend(i)
    }
}