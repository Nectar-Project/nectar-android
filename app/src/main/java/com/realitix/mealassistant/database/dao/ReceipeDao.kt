package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeRaw
import com.realitix.mealassistant.database.entity.ReceipeStep

@Dao
interface ReceipeDao {
    @Transaction
    @Query("SELECT * FROM ReceipeRaw")
    fun list(): LiveData<List<Receipe>>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE name LIKE '%' ||  :search || '%'")
    fun search(search: String): LiveData<List<Receipe>>

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid=:uuid")
    fun get(uuid: String): LiveData<Receipe>

    @Update
    suspend fun update(receipe: ReceipeRaw)

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE uuid=:uuid")
    suspend fun has(uuid: String): Receipe?

    @Insert
    suspend fun insert(receipe: ReceipeRaw): Long

    @Transaction
    @Query("SELECT * FROM ReceipeRaw WHERE ReceipeRaw.uuid=:uuid")
    fun getFull(uuid: String): LiveData<Receipe>
}