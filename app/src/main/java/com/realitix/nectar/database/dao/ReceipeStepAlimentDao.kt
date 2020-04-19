package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeStepAliment
import com.realitix.nectar.database.entity.ReceipeStepAlimentRaw

@Dao
abstract class ReceipeStepAlimentDao: GenericGetJoinDao<ReceipeStepAlimentRaw, ReceipeStepAliment>() {
    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw WHERE stepUuid = :uuid1 AND alimentUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<ReceipeStepAliment>

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw WHERE stepUuid = :uuid1 AND alimentUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): ReceipeStepAliment?

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw WHERE stepUuid = :uuid1 AND alimentUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): ReceipeStepAliment?

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw")
    abstract override fun list(): List<ReceipeStepAliment>

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw")
    abstract override fun listLive(): LiveData<List<ReceipeStepAliment>>

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw")
    abstract override suspend fun listSuspend(): List<ReceipeStepAliment>
}