package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.mealassistant.database.entity.AlimentCategory
import com.realitix.mealassistant.database.entity.AlimentCategoryRaw

@Dao
interface AlimentCategoryDao {
    @Transaction
    @Query("SELECT * FROM AlimentCategoryRaw WHERE name=:name")
    fun getByName(name: String?): AlimentCategory?

    @Insert
    fun insert(alimentCategory: AlimentCategoryRaw?): Long
}