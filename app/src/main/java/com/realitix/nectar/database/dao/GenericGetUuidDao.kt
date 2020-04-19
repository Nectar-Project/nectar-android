package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*

abstract class GenericGetUuidDao<in ERaw, E>: GenericCrudDao<ERaw, E>() {
    abstract fun get(uuid: String): E?
    abstract fun getLive(uuid: String): LiveData<E>
    abstract suspend fun getSuspend(uuid: String): E?
}