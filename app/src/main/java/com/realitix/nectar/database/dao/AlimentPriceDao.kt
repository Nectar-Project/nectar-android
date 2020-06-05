package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentPrice
import com.realitix.nectar.database.entity.AlimentPriceRaw
import com.realitix.nectar.database.entity.AlimentTag
import com.realitix.nectar.database.entity.AlimentTagRaw

@Dao
abstract class AlimentPriceDao: GenericGetJoinDao<AlimentPriceRaw, AlimentPrice>() {
    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw WHERE alimentUuid = :uuid1")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<AlimentPrice>

    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw WHERE alimentUuid = :uuid1")
    abstract override fun get(uuid1: String, uuid2: String): AlimentPrice?

    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw WHERE alimentUuid = :uuid1")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): AlimentPrice?

    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw")
    abstract override fun list(): List<AlimentPrice>

    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw")
    abstract override fun listLive(): LiveData<List<AlimentPrice>>

    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw")
    abstract override suspend fun listSuspend(): List<AlimentPrice>
}