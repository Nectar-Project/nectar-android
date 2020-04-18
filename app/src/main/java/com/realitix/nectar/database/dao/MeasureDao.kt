package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class MeasureDao: BaseDao<MeasureRaw, Measure>() {
    @Transaction
    @Query("SELECT * FROM MeasureRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Measure>

    @Transaction
    @Query("SELECT * FROM MeasureRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Measure?

    @Transaction
    @Query("SELECT * FROM MeasureRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Measure?

    @Transaction
    @Query("SELECT * FROM MeasureRaw")
    abstract override fun list(): List<Measure>

    @Transaction
    @Query("SELECT * FROM MeasureRaw")
    abstract override fun listLive(): LiveData<List<Measure>>

    @Transaction
    @Query("SELECT * FROM MeasureRaw")
    abstract override suspend fun listSuspend(): List<Measure>
}