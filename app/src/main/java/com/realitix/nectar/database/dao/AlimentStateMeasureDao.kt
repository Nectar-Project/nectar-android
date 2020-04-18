package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.database.entity.AlimentStateMeasure
import com.realitix.nectar.database.entity.AlimentStateMeasureRaw

@Dao
abstract class AlimentStateMeasureDao: BaseDao<AlimentStateMeasureRaw, AlimentStateMeasure>() {
    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw WHERE alimentStateUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<AlimentStateMeasure>

    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw WHERE alimentStateUuid = :uuid")
    abstract override fun get(uuid: String): AlimentStateMeasure?

    @Transaction
    @Query("SELECT * FROM AlimentStateMeasureRaw WHERE alimentStateUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): AlimentStateMeasure?

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