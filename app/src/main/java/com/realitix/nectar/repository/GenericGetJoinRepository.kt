package com.realitix.nectar.repository

import com.realitix.nectar.database.dao.GenericGetJoinDao

abstract class GenericGetJoinRepository<ERaw, E>(listener: EntityUpdaterInterface<ERaw>): GenericCrudRepository<ERaw, E>(listener) {
    abstract override fun getDao(): GenericGetJoinDao<ERaw, E>

    // get
    fun get(uuid1: String, uuid2: String) = getDao().get(uuid1, uuid2)
    suspend fun getSuspend(uuid1: String, uuid2: String) = getDao().getSuspend(uuid1, uuid2)
    fun getLive(uuid1: String, uuid2: String) = getDao().getLive(uuid1, uuid2)
}