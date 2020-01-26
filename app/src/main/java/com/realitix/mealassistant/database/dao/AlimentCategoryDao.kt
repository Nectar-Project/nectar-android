package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.realitix.mealassistant.database.entity.AlimentCategory

@Dao
interface AlimentCategoryDao {
    @Query("SELECT * FROM AlimentCategory WHERE name=:name")
    fun getByName(name: String?): AlimentCategory?

    @Insert
    fun insert(alimentCategory: AlimentCategory?): Long
}