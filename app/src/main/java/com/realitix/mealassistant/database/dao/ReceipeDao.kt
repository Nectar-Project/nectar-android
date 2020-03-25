package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeNameRaw
import com.realitix.mealassistant.database.entity.ReceipeRaw
import com.realitix.mealassistant.database.entity.ReceipeStep

@Dao
interface ReceipeDao {
    @Transaction
    @Query("SELECT * FROM ReceipeRaw")
    fun list(): LiveData<List<Receipe>>

    @Transaction
    @Query("""
        SELECT ReceipeRaw.*
        FROM ReceipeRaw
        INNER JOIN ReceipeNameRaw ON ReceipeNameRaw.receipeUuid = ReceipeRaw.uuid
        INNER JOIN ReceipeNameFts ON ReceipeNameRaw.rowid = ReceipeNameFts.rowid
        WHERE ReceipeNameFts MATCH :term
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

    @Update
    suspend fun updateName(receipeName: ReceipeNameRaw)

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid=:uuid")
    suspend fun has(uuid: String): Receipe?

    @Insert
    suspend fun insertSuspended(receipe: ReceipeRaw)

    @Insert
    fun insert(receipe: ReceipeRaw)

    @Insert
    suspend fun insertName(receipeName: ReceipeNameRaw)

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE ReceipeRaw.uuid=:uuid")
    fun getFull(uuid: String): LiveData<Receipe>
}