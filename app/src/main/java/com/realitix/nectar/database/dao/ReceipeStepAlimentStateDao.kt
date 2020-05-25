package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeStepAlimentState
import com.realitix.nectar.database.entity.ReceipeStepAlimentStateRaw

@Dao
abstract class ReceipeStepAlimentStateDao: GenericGetJoinDao<ReceipeStepAlimentStateRaw, ReceipeStepAlimentState>() {
    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentStateRaw WHERE stepUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<ReceipeStepAlimentState>

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentStateRaw WHERE stepUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): ReceipeStepAlimentState?

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentStateRaw WHERE stepUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): ReceipeStepAlimentState?

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentStateRaw")
    abstract override fun list(): List<ReceipeStepAlimentState>

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentStateRaw")
    abstract override fun listLive(): LiveData<List<ReceipeStepAlimentState>>

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentStateRaw")
    abstract override suspend fun listSuspend(): List<ReceipeStepAlimentState>
}