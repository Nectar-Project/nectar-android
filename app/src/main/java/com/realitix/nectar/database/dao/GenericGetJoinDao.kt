package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*

abstract class GenericGetJoinDao<in ERaw, E>: GenericCrudDao<ERaw, E>() {
    abstract fun get(uuid1: String, uuid2: String): E?
    abstract fun getLive(uuid1: String, uuid2: String): LiveData<E>
    abstract suspend fun getSuspend(uuid1: String, uuid2: String): E?
}