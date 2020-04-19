package com.realitix.nectar.repository

import com.realitix.nectar.database.dao.GenericCrudDao

abstract class GenericCrudRepository<ERaw, E>(private val entityUpdaterListener: EntityUpdaterInterface<ERaw>) {
    abstract fun getDao(): GenericCrudDao<ERaw, E>

    interface EntityUpdaterInterface<ERaw> {
        fun onEntityUpdate(entity: ERaw)
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
        entityUpdaterListener.onEntityUpdate(i)
    }

    // update
    open fun update(i: ERaw) {
        getDao().update(i)
        entityUpdaterListener.onEntityUpdate(i)
    }

    open suspend fun updateSuspend(i: ERaw) {
        getDao().updateSuspend(i)
        entityUpdaterListener.onEntityUpdate(i)
    }

    // delete
    open fun delete(i: ERaw) {
        getDao().delete(i)
        entityUpdaterListener.onEntityUpdate(i)
    }

    open suspend fun deleteSuspend(i: ERaw) {
        getDao().deleteSuspend(i)
        entityUpdaterListener.onEntityUpdate(i)
    }
}