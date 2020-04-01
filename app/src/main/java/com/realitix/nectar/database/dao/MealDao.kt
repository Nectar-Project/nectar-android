package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.nectar.database.entity.Meal
import com.realitix.nectar.database.entity.MealRaw

@Dao
interface MealDao: BaseDao<MealRaw> {
    @Transaction
    @Query("SELECT * FROM MealRaw WHERE timestamp BETWEEN :begin AND :end ORDER BY timestamp")
    fun search(begin: Long, end: Long): LiveData<List<Meal>>

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE uuid=:uuid")
    fun get(uuid: String): Meal?

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE uuid=:uuid")
    fun getLive(uuid: String): LiveData<Meal>
}