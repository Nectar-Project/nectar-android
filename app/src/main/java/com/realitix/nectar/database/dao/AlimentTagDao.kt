package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentTag
import com.realitix.nectar.database.entity.AlimentTagRaw

@Dao
abstract class AlimentTagDao: GenericGetJoinDao<AlimentTagRaw, AlimentTag>() {
    @Transaction
    @Query("SELECT * FROM AlimentTagRaw WHERE alimentUuid = :uuid1 AND tagUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<AlimentTag>

    @Transaction
    @Query("SELECT * FROM AlimentTagRaw WHERE alimentUuid = :uuid1 AND tagUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): AlimentTag?

    @Transaction
    @Query("SELECT * FROM AlimentTagRaw WHERE alimentUuid = :uuid1 AND tagUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): AlimentTag?

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