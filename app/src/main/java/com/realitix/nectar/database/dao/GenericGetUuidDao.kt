package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.nectar.database.entity.UuidInterface

abstract class GenericGetUuidDao<in ERaw, E: UuidInterface>: GenericCrudDao<ERaw, E>() {
    abstract fun get(uuid: String): E?
    abstract fun getLive(uuid: String): LiveData<E>
    abstract suspend fun getSuspend(uuid: String): E?
}