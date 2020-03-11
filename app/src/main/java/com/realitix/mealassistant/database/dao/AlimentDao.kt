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
    @Query("SELECT * FROM AlimentRaw WHERE nameSearch LIKE '%' ||  :search || '%'")
    fun search(search: String): LiveData<List<Aliment>>

    @Transaction
    @Query("SELECT * FROM AlimentRaw WHERE name LIKE :name")
    fun getByName(name: String?): Aliment?

    @Transaction
    @Query("SELECT * FROM AlimentRaw WHERE id = :id")
    fun get(id: Long): LiveData<Aliment>

    @Insert
    fun insert(aliment: AlimentRaw?): Long
}