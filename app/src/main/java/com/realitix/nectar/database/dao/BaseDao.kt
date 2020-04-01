package com.realitix.nectar.database.dao

import androidx.room.*

interface BaseDao<in ERaw> {
    @Insert
    fun insert(obj: ERaw)
    @Insert
    suspend fun insertSuspend(obj: ERaw)

    @Update
    fun update(obj: ERaw)
    @Update
    suspend fun updateSuspend(obj: ERaw)

    @Delete
    fun delete(obj: ERaw)
    @Delete
    suspend fun deleteSuspend(obj: ERaw)
}