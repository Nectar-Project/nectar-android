package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeStepReceipe
import com.realitix.nectar.database.entity.ReceipeStepReceipeRaw

@Dao
abstract class ReceipeStepReceipeDao: GenericGetJoinDao<ReceipeStepReceipeRaw, ReceipeStepReceipe>() {
    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw WHERE stepUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<ReceipeStepReceipe>

    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw WHERE stepUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): ReceipeStepReceipe?

    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw WHERE stepUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): ReceipeStepReceipe?

    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw")
    abstract override fun list(): List<ReceipeStepReceipe>

    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw")
    abstract override fun listLive(): LiveData<List<ReceipeStepReceipe>>

    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw")
    abstract override suspend fun listSuspend(): List<ReceipeStepReceipe>
}