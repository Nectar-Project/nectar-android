package com.realitix.nectar.repository

import com.realitix.nectar.database.dao.GenericGetUuidDao

abstract class GenericGetUuidRepository<ERaw, E>(listener: EntityUpdaterInterface<ERaw>): GenericCrudRepository<ERaw, E>(listener) {
    abstract override fun getDao(): GenericGetUuidDao<ERaw, E>

    // get
    fun get(uuid: String) = getDao().get(uuid)
    suspend fun getSuspend(uuid: String) = getDao().getSuspend(uuid)
    fun getLive(uuid: String) = getDao().getLive(uuid)
}