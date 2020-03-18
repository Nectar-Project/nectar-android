package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.AlimentRaw

@Dao
interface AlimentDao {
    @Transaction
    @Query("""
        SELECT AlimentRaw.*
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
    fun get(uuid: String): LiveData<Aliment>

    @Insert
    fun insert(aliment: AlimentRaw): Long
}