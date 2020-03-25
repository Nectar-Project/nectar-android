package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.mealassistant.database.entity.*

@Dao
interface ReceipeStepDao {
    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE uuid=:uuid")
    operator fun get(uuid: String): ReceipeStep?

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE uuid=:uuid")
    fun getFull(uuid: String): LiveData<ReceipeStep>

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE uuid=:uuid")
    suspend fun has(uuid: String): ReceipeStep?

    @Insert
    suspend fun insertSuspended(receipeStep: ReceipeStepRaw)

    @Insert
    fun insert(receipeStep: ReceipeStepRaw)
}