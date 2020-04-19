package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentImage
import com.realitix.nectar.database.entity.AlimentImageRaw

@Dao
abstract class AlimentImageDao: GenericGetJoinDao<AlimentImageRaw, AlimentImage>() {
    @Transaction
    @Query("SELECT * FROM AlimentImageRaw WHERE alimentUuid = :uuid1 AND imageUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<AlimentImage>

    @Transaction
    @Query("SELECT * FROM AlimentImageRaw WHERE alimentUuid = :uuid1 AND imageUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): AlimentImage?

    @Transaction
    @Query("SELECT * FROM AlimentImageRaw WHERE alimentUuid = :uuid1 AND imageUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): AlimentImage?

    @Transaction
    @Query("SELECT * FROM AlimentImageRaw")
    abstract override fun list(): List<AlimentImage>

    @Transaction
    @Query("SELECT * FROM AlimentImageRaw")
    abstract override fun listLive(): LiveData<List<AlimentImage>>

    @Transaction
    @Query("SELECT * FROM AlimentImageRaw")
    abstract override suspend fun listSuspend(): List<AlimentImage>
}