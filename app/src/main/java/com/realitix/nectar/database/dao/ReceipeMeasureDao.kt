package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeMeasure
import com.realitix.nectar.database.entity.ReceipeMeasureRaw
import com.realitix.nectar.database.entity.ReceipeTag
import com.realitix.nectar.database.entity.ReceipeTagRaw

@Dao
abstract class ReceipeMeasureDao: GenericGetJoinDao<ReceipeMeasureRaw, ReceipeMeasure>() {
    @Transaction
    @Query("SELECT * FROM ReceipeMeasureRaw WHERE receipeUuid = :uuid1 AND measureUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<ReceipeMeasure>

    @Transaction
    @Query("SELECT * FROM ReceipeMeasureRaw WHERE receipeUuid = :uuid1 AND measureUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): ReceipeMeasure?

    @Transaction
    @Query("SELECT * FROM ReceipeMeasureRaw WHERE receipeUuid = :uuid1 AND measureUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): ReceipeMeasure?

    @Transaction
    @Query("SELECT * FROM ReceipeMeasureRaw")
    abstract override fun list(): List<ReceipeMeasure>

    @Transaction
    @Query("SELECT * FROM ReceipeMeasureRaw")
    abstract override fun listLive(): LiveData<List<ReceipeMeasure>>

    @Transaction
    @Query("SELECT * FROM ReceipeMeasureRaw")
    abstract override suspend fun listSuspend(): List<ReceipeMeasure>
}