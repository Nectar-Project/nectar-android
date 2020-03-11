package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.mealassistant.database.entity.Meal
import com.realitix.mealassistant.database.entity.MealAliment
import com.realitix.mealassistant.database.entity.MealRaw
import com.realitix.mealassistant.database.entity.MealReceipe

@Dao
interface MealDao {
    @Transaction
    @Query("SELECT * FROM MealRaw WHERE timestamp BETWEEN :begin AND :end ORDER BY timestamp")
    fun search(begin: Long, end: Long): LiveData<List<Meal>>

    @Insert
    suspend fun insert(meal: MealRaw): Long

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE id=:id")
    suspend fun has(id: Long): Meal?

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE id=:id")
    operator fun get(id: Long): Meal?

    @Transaction
    @Query("SELECT * FROM MealRaw WHERE id=:id")
    fun getFull(id: Long): LiveData<Meal>
}