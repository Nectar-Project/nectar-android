package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeRaw

@Dao
abstract class ReceipeDao: BaseDao<ReceipeRaw, Receipe>() {
    @Transaction
    @Query("""
        SELECT DISTINCT ReceipeRaw.*
        FROM ReceipeRaw
        INNER JOIN StringKeyRaw ON StringKeyRaw.uuid = ReceipeRaw.nameUuid
        INNER JOIN StringKeyValueRaw ON StringKeyValueRaw.stringKeyUuid = StringKeyRaw.uuid
        INNER JOIN StringKeyValueFts ON StringKeyValueFts.rowid = StringKeyValueRaw.rowid
        WHERE StringKeyValueFts MATCH :term
    """)
    abstract fun search(term: String): LiveData<List<Receipe>>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Receipe>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Receipe?

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Receipe?

    @Transaction
    @Query("SELECT * FROM ReceipeRaw")
    abstract override fun list(): List<Receipe>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw")
    abstract override fun listLive(): LiveData<List<Receipe>>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw")
    abstract override suspend fun listSuspend(): List<Receipe>
}