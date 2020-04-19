package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.MealAliment
import com.realitix.nectar.database.entity.MealAlimentRaw

@Dao
abstract class MealAlimentDao: GenericGetJoinDao<MealAlimentRaw, MealAliment>() {
    @Transaction
    @Query("SELECT * FROM MealAlimentRaw WHERE mealUuid = :uuid1 AND alimentUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<MealAliment>

    @Transaction
    @Query("SELECT * FROM MealAlimentRaw WHERE mealUuid = :uuid1 AND alimentUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): MealAliment?

    @Transaction
    @Query("SELECT * FROM MealAlimentRaw WHERE mealUuid = :uuid1 AND alimentUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): MealAliment?

    @Transaction
    @Query("SELECT * FROM MealAlimentRaw")
    abstract override fun list(): List<MealAliment>

    @Transaction
    @Query("SELECT * FROM MealAlimentRaw")
    abstract override fun listLive(): LiveData<List<MealAliment>>

    @Transaction
    @Query("SELECT * FROM MealAlimentRaw")
    abstract override suspend fun listSuspend(): List<MealAliment>
}