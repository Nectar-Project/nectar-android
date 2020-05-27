package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.MealAlimentState
import com.realitix.nectar.database.entity.MealAlimentStateRaw

@Dao
abstract class MealAlimentDao: GenericGetJoinDao<MealAlimentStateRaw, MealAlimentState>() {
    @Transaction
    @Query("SELECT * FROM MealAlimentStateRaw WHERE mealUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<MealAlimentState>

    @Transaction
    @Query("SELECT * FROM MealAlimentStateRaw WHERE mealUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): MealAlimentState?

    @Transaction
    @Query("SELECT * FROM MealAlimentStateRaw WHERE mealUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): MealAlimentState?

    @Transaction
    @Query("SELECT * FROM MealAlimentStateRaw")
    abstract override fun list(): List<MealAlimentState>

    @Transaction
    @Query("SELECT * FROM MealAlimentStateRaw")
    abstract override fun listLive(): LiveData<List<MealAlimentState>>

    @Transaction
    @Query("SELECT * FROM MealAlimentStateRaw")
    abstract override suspend fun listSuspend(): List<MealAlimentState>
}