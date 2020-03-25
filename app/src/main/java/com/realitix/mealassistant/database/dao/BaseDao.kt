package com.realitix.mealassistant.database.dao

import androidx.room.*


interface BaseDao<T> {
    @Insert
    fun insert(obj: T)
    @Update
    fun update(obj: T)
    @Delete
    fun delete(obj: T)
}