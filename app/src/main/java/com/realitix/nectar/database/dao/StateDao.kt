package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.State
import com.realitix.nectar.database.entity.StateRaw

@Dao
abstract class StateDao: GenericGetUuidDao<StateRaw, State>() {
    @Transaction
    @Query("SELECT * FROM StateRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<State>

    @Transaction
    @Query("SELECT * FROM StateRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): State?

    @Transaction
    @Query("SELECT * FROM StateRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): State?

    @Transaction
    @Query("SELECT * FROM StateRaw")
    abstract override fun list(): List<State>

    @Transaction
    @Query("SELECT * FROM StateRaw")
    abstract override fun listLive(): LiveData<List<State>>

    @Transaction
    @Query("SELECT * FROM StateRaw")
    abstract override suspend fun listSuspend(): List<State>
}