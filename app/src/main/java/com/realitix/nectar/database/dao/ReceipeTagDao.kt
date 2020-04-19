package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeTag
import com.realitix.nectar.database.entity.ReceipeTagRaw

@Dao
abstract class ReceipeTagDao: GenericGetJoinDao<ReceipeTagRaw, ReceipeTag>() {
    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw WHERE receipeUuid = :uuid1 AND tagUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<ReceipeTag>

    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw WHERE receipeUuid = :uuid1 AND tagUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): ReceipeTag?

    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw WHERE receipeUuid = :uuid1 AND tagUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): ReceipeTag?

    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw")
    abstract override fun list(): List<ReceipeTag>

    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw")
    abstract override fun listLive(): LiveData<List<ReceipeTag>>

    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw")
    abstract override suspend fun listSuspend(): List<ReceipeTag>
}