package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.nectar.database.entity.*

@Dao
abstract class ReceipeStepDao: GenericGetUuidDao<ReceipeStepRaw, ReceipeStep>() {
    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<ReceipeStep>

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): ReceipeStep?

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): ReceipeStep?

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw")
    abstract override fun list(): List<ReceipeStep>

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw")
    abstract override fun listLive(): LiveData<List<ReceipeStep>>

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw")
    abstract override suspend fun listSuspend(): List<ReceipeStep>

    @Transaction
    @Query("SELECT * FROM ReceipeStepRaw WHERE receipeUuid = :uuid")
    abstract suspend fun listByReceipeSuspend(uuid: String): List<ReceipeStep>
}