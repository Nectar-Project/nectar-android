package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeStepAliment
import com.realitix.nectar.database.entity.ReceipeStepAlimentRaw

@Dao
abstract class ReceipeStepAlimentDao: BaseDao<ReceipeStepAlimentRaw, ReceipeStepAliment>() {
    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw WHERE stepUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<ReceipeStepAliment>

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw WHERE stepUuid = :uuid")
    abstract override fun get(uuid: String): ReceipeStepAliment?

    @Transaction
    @Query("SELECT * FROM ReceipeStepAlimentRaw WHERE stepUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): ReceipeStepAliment?

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