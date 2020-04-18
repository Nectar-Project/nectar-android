package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.MealReceipe
import com.realitix.nectar.database.entity.MealReceipeRaw

@Dao
abstract class MealReceipeDao: BaseDao<MealReceipeRaw, MealReceipe>() {
    @Transaction
    @Query("SELECT * FROM MealReceipeRaw WHERE mealUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<MealReceipe>

    @Transaction
    @Query("SELECT * FROM MealReceipeRaw WHERE mealUuid = :uuid")
    abstract override fun get(uuid: String): MealReceipe?

    @Transaction
    @Query("SELECT * FROM MealReceipeRaw WHERE mealUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): MealReceipe?

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