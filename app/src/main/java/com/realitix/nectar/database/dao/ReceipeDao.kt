package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeRaw

@Dao
interface ReceipeDao {
    @Transaction
    @Query("SELECT * FROM ReceipeRaw")
    fun list(): LiveData<List<Receipe>>

    @Transaction
    @Query("""
        SELECT DISTINCT ReceipeRaw.*
        FROM ReceipeRaw
        INNER JOIN StringKeyRaw ON StringKeyRaw.uuid = ReceipeRaw.nameUuid
        INNER JOIN StringKeyValueRaw ON StringKeyValueRaw.stringKeyUuid = StringKeyRaw.uuid
        INNER JOIN StringKeyValueFts ON StringKeyValueFts.rowid = StringKeyValueRaw.rowid
        WHERE StringKeyValueFts MATCH :term
    """)
    fun search(term: String): LiveData<List<Receipe>>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid=:uuid")
    fun getLive(uuid: String): LiveData<Receipe>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid=:uuid")
    fun get(uuid: String): Receipe?

    @Update
    suspend fun update(receipe: ReceipeRaw)

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid=:uuid")
    suspend fun has(uuid: String): Receipe?

    @Insert
    suspend fun insertSuspended(receipe: ReceipeRaw)

    @Insert
    fun insert(receipe: ReceipeRaw)

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE ReceipeRaw.uuid=:uuid")
    fun getFull(uuid: String): LiveData<Receipe>
}