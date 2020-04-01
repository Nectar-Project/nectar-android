package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentRaw

@Dao
interface AlimentDao: BaseDao<AlimentRaw> {
    @Transaction
    @Query("""
        SELECT DISTINCT AlimentRaw.*
        FROM AlimentRaw
        INNER JOIN AlimentNameRaw ON AlimentNameRaw.alimentUuid = AlimentRaw.uuid
        INNER JOIN AlimentNameFts ON AlimentNameRaw.rowid = AlimentNameFts.rowid
        WHERE AlimentNameFts MATCH :term
    """)
    fun search(term: String): LiveData<List<Aliment>>

    @Transaction
    @Query("""
        SELECT AlimentRaw.*
        FROM AlimentRaw
        INNER JOIN AlimentNameRaw ON AlimentNameRaw.alimentUuid = AlimentRaw.uuid
        WHERE AlimentNameRaw.name LIKE :name
    """)
    fun getByName(name: String): Aliment?

    @Transaction
    @Query("SELECT * FROM AlimentRaw WHERE uuid = :uuid")
    fun getLive(uuid: String): LiveData<Aliment>

    @Transaction
    @Query("SELECT * FROM AlimentRaw WHERE uuid = :uuid")
    fun get(uuid: String): Aliment?
}