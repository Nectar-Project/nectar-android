package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.database.entity.AlimentStateRaw


@Dao
abstract class AlimentStateDao: BaseDao<AlimentStateRaw, AlimentState>() {
    @Transaction
    @Query("SELECT * FROM AlimentStateRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<AlimentState>

    @Transaction
    @Query("SELECT * FROM AlimentStateRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): AlimentState?

    @Transaction
    @Query("SELECT * FROM AlimentStateRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): AlimentState?

    @Transaction
    @Query("SELECT * FROM AlimentStateRaw")
    abstract override fun list(): List<AlimentState>

    @Transaction
    @Query("SELECT * FROM AlimentStateRaw")
    abstract override fun listLive(): LiveData<List<AlimentState>>

    @Transaction
    @Query("SELECT * FROM AlimentStateRaw")
    abstract override suspend fun listSuspend(): List<AlimentState>
}