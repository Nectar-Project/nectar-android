package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.mealassistant.database.entity.*

@Dao
interface ReceipeStepDao {
    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE id=:id")
    operator fun get(id: Long): ReceipeStep?

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE id=:id")
    fun getFull(id: Long): LiveData<ReceipeStep>

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE id=:id")
    suspend fun has(id: Long): ReceipeStep?

    @Insert
    suspend fun insert(receipeStep: ReceipeStepRaw): Long
}