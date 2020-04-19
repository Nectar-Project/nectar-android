package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentRaw

@Dao
abstract class AlimentDao: GenericGetUuidDao<AlimentRaw, Aliment>() {
    @Transaction
    @Query(
        """
        SELECT DISTINCT AlimentRaw.*
        FROM AlimentRaw
        INNER JOIN StringKeyRaw ON StringKeyRaw.uuid = AlimentRaw.nameUuid
        INNER JOIN StringKeyValueRaw ON StringKeyValueRaw.stringKeyUuid = StringKeyRaw.uuid
        INNER JOIN StringKeyValueFts ON StringKeyValueFts.rowid = StringKeyValueRaw.rowid
        WHERE StringKeyValueFts MATCH :term
    """
    )
    abstract fun search(term: String): LiveData<List<Aliment>>

    @Transaction
    @Query("SELECT * FROM AlimentRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Aliment>

    @Transaction
    @Query("SELECT * FROM AlimentRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Aliment?

    @Transaction
    @Query("SELECT * FROM AlimentRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Aliment?

    @Transaction
    @Query("SELECT * FROM AlimentRaw ")
    abstract override fun list(): List<Aliment>

    @Transaction
    @Query("SELECT * FROM AlimentRaw ")
    abstract override fun listLive(): LiveData<List<Aliment>>

    @Transaction
    @Query("SELECT * FROM AlimentRaw ")
    abstract override suspend fun listSuspend(): List<Aliment>
}