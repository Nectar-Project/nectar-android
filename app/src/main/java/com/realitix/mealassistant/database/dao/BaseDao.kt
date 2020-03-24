package com.realitix.mealassistant.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update


interface BaseDao<T> {
    @Insert
    fun insert(obj: T)
    @Update
    fun update(obj: T)
    @Delete
    fun delete(obj: T)
}