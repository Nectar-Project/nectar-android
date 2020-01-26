package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.realitix.mealassistant.database.entity.Aliment

@Dao
interface AlimentDao {
    @Query("SELECT * FROM Aliment WHERE nameSearch LIKE '%' ||  :search || '%'")
    fun search(search: String?): List<Aliment?>?

    @Query("SELECT * FROM Aliment WHERE name LIKE :name")
    fun getByName(name: String?): Aliment?

    @Insert
    fun insert(aliment: Aliment?): Long
}