package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class StringKeyValueDao: GenericGetJoinDao<StringKeyValueRaw, StringKeyValue>() {
    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid = :uuid1 AND language = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<StringKeyValue>

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid = :uuid1 AND language = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): StringKeyValue?

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid = :uuid1 AND language = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): StringKeyValue?

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw")
    abstract override fun list(): List<StringKeyValue>

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw")
    abstract override fun listLive(): LiveData<List<StringKeyValue>>

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw")
    abstract override suspend fun listSuspend(): List<StringKeyValue>
}