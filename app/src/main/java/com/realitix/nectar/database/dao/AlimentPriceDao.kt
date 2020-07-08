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
abstract class AlimentPriceDao: GenericGetUuidDao<AlimentPriceRaw, AlimentPrice>() {
    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<AlimentPrice>

    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): AlimentPrice?

    @Transaction
    @Query("SELECT * FROM AlimentPriceRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): AlimentPrice?

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