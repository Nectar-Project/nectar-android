package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class StringKeyDao: BaseDao<StringKeyRaw, StringKey>() {
    @Transaction
    @Query("SELECT * FROM StringKeyRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<StringKey>

    @Transaction
    @Query("SELECT * FROM StringKeyRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): StringKey?

    @Transaction
    @Query("SELECT * FROM StringKeyRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): StringKey?

    @Transaction
    @Query("SELECT * FROM StringKeyRaw")
    abstract override fun list(): List<StringKey>

    @Transaction
    @Query("SELECT * FROM StringKeyRaw")
    abstract override fun listLive(): LiveData<List<StringKey>>

    @Transaction
    @Query("SELECT * FROM StringKeyRaw")
    abstract override suspend fun listSuspend(): List<StringKey>
}