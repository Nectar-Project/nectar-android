package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
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