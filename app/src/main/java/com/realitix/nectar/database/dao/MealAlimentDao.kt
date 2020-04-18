package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.MealAliment
import com.realitix.nectar.database.entity.MealAlimentRaw

@Dao
abstract class MealAlimentDao: BaseDao<MealAlimentRaw, MealAliment>() {
    @Transaction
    @Query("SELECT * FROM MealAlimentRaw WHERE mealUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<MealAliment>

    @Transaction
    @Query("SELECT * FROM MealAlimentRaw WHERE mealUuid = :uuid")
    abstract override fun get(uuid: String): MealAliment?

    @Transaction
    @Query("SELECT * FROM MealAlimentRaw WHERE mealUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): MealAliment?

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