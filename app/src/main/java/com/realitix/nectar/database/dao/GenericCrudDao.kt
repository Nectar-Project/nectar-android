package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*

abstract class GenericCrudDao<in ERaw, E> {
    abstract fun list(): List<@JvmSuppressWildcards E>
    abstract fun listLive(): LiveData<List<@JvmSuppressWildcards E>>
    abstract suspend fun listSuspend(): List<@JvmSuppressWildcards E>

    @Insert
    abstract fun insert(obj: ERaw)
    @Insert
    abstract suspend fun insertSuspend(obj: ERaw)

    @Update
    abstract fun update(obj: ERaw)
    @Update
    abstract suspend fun updateSuspend(obj: ERaw)

    @Delete
    abstract fun delete(obj: ERaw)
    @Delete
    abstract suspend fun deleteSuspend(obj: ERaw)
}