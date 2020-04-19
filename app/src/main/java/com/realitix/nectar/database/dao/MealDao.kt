package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.nectar.database.entity.Meal
import com.realitix.nectar.database.entity.MealRaw

@Dao
abstract class MealDao: GenericGetUuidDao<MealRaw, Meal>() {
    @Transaction
    @Query("SELECT * FROM MealRaw WHERE timestamp BETWEEN :begin AND :end ORDER BY timestamp")
    abstract fun search(begin: Long, end: Long): LiveData<List<Meal>>

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Meal>

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Meal?

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Meal?

    @Transaction
    @Query("SELECT * FROM MealRaw")
    abstract override fun list(): List<Meal>

    @Transaction
    @Query("SELECT * FROM MealRaw")
    abstract override fun listLive(): LiveData<List<Meal>>

    @Transaction
    @Query("SELECT * FROM MealRaw")
    abstract override suspend fun listSuspend(): List<Meal>
}