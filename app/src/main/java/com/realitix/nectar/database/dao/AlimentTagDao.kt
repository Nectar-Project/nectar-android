package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentTag
import com.realitix.nectar.database.entity.AlimentTagRaw

@Dao
abstract class AlimentTagDao: BaseDao<AlimentTagRaw, AlimentTag>() {
    @Transaction
    @Query("SELECT * FROM AlimentTagRaw WHERE alimentUuid  = :uuid")
    abstract override fun getLive(uuid: String): LiveData<AlimentTag>

    @Transaction
    @Query("SELECT * FROM AlimentTagRaw WHERE alimentUuid = :uuid")
    abstract override fun get(uuid: String): AlimentTag?

    @Transaction
    @Query("SELECT * FROM AlimentTagRaw WHERE alimentUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): AlimentTag?

    @Transaction
    @Query("SELECT * FROM AlimentTagRaw")
    abstract override fun list(): List<AlimentTag>

    @Transaction
    @Query("SELECT * FROM AlimentTagRaw")
    abstract override fun listLive(): LiveData<List<AlimentTag>>

    @Transaction
    @Query("SELECT * FROM AlimentTagRaw")
    abstract override suspend fun listSuspend(): List<AlimentTag>
}