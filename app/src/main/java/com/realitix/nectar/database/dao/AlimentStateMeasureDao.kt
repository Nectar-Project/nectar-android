package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentStateMeasure
import com.realitix.nectar.database.entity.AlimentStateMeasureRaw

@Dao
abstract class AlimentStateMeasureDao: GenericGetJoinDao<AlimentStateMeasureRaw, AlimentStateMeasure>() {
    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw WHERE alimentStateUuid = :uuid1 AND measureUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<AlimentStateMeasure>

    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw WHERE alimentStateUuid = :uuid1 AND measureUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): AlimentStateMeasure?

    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw WHERE alimentStateUuid = :uuid1 AND measureUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): AlimentStateMeasure?

    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw")
    abstract override fun list(): List<AlimentStateMeasure>

    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw")
    abstract override fun listLive(): LiveData<List<AlimentStateMeasure>>

    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw")
    abstract override suspend fun listSuspend(): List<AlimentStateMeasure>
}