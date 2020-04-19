package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.MealReceipe
import com.realitix.nectar.database.entity.MealReceipeRaw

@Dao
abstract class MealReceipeDao: GenericGetJoinDao<MealReceipeRaw, MealReceipe>() {
    @Transaction
    @Query("SELECT * FROM MealReceipeRaw WHERE mealUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<MealReceipe>

    @Transaction
    @Query("SELECT * FROM MealReceipeRaw WHERE mealUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): MealReceipe?

    @Transaction
    @Query("SELECT * FROM MealReceipeRaw WHERE mealUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): MealReceipe?

    @Transaction
    @Query("SELECT * FROM MealReceipeRaw")
    abstract override fun list(): List<MealReceipe>

    @Transaction
    @Query("SELECT * FROM MealReceipeRaw")
    abstract override fun listLive(): LiveData<List<MealReceipe>>

    @Transaction
    @Query("SELECT * FROM MealReceipeRaw")
    abstract override suspend fun listSuspend(): List<MealReceipe>
}