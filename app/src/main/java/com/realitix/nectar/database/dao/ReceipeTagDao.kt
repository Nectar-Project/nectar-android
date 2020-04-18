package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeTag
import com.realitix.nectar.database.entity.ReceipeTagRaw

@Dao
abstract class ReceipeTagDao: BaseDao<ReceipeTagRaw, ReceipeTag>() {
    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw WHERE receipeUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<ReceipeTag>

    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw WHERE receipeUuid = :uuid")
    abstract override fun get(uuid: String): ReceipeTag?

    @Transaction
    @Query("SELECT * FROM ReceipeTagRaw WHERE receipeUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): ReceipeTag?

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