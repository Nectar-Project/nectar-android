package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeStepReceipe
import com.realitix.nectar.database.entity.ReceipeStepReceipeRaw

@Dao
abstract class ReceipeStepReceipeDao: BaseDao<ReceipeStepReceipeRaw, ReceipeStepReceipe>() {
    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw WHERE stepUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<ReceipeStepReceipe>

    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw WHERE stepUuid = :uuid")
    abstract override fun get(uuid: String): ReceipeStepReceipe?

    @Transaction
    @Query("SELECT * FROM ReceipeStepReceipeRaw WHERE stepUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): ReceipeStepReceipe?

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