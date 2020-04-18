package com.realitix.nectar.repository

import com.realitix.nectar.database.dao.BaseDao

abstract class GenericRepository<ERaw, E> {
    abstract fun getDao(): BaseDao<ERaw, E>

    // get
    fun get(uuid: String) = getDao().get(uuid)
    suspend fun getSuspend(uuid: String) = getDao().getSuspend(uuid)
    fun getLive(uuid: String) = getDao().getLive(uuid)

    // list
    fun list() = getDao().list()
    suspend fun listSuspend() = getDao().listSuspend()
    fun listLive() = getDao().listLive()

    // insert
    open fun insert(i: ERaw) = getDao().insert(i)
    open suspend fun insertSuspend(i: ERaw) = getDao().insertSuspend(i)

    // update
    open fun update(i: ERaw) = getDao().update(i)
    open suspend fun updateSuspend(i: ERaw) = getDao().updateSuspend(i)
}