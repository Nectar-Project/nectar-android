package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class StringKeyValueDao: BaseDao<StringKeyValueRaw, StringKeyValue>() {
    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid=:uuid AND language=:lang")
    abstract fun getLang(uuid: String, lang: String): StringKeyValue?

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid=:uuid AND language=:lang")
    abstract suspend fun getLangSuspend(uuid: String, lang: String): StringKeyValue?

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<StringKeyValue>

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid = :uuid")
    abstract override fun get(uuid: String): StringKeyValue?

    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): StringKeyValue?

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