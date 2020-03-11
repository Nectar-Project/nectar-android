package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.realitix.mealassistant.database.entity.ReceipeStepReceipeRaw

@Dao
interface ReceipeStepReceipeDao {
    @Insert
    fun insert(receipeStepReceipe: ReceipeStepReceipeRaw): Long
}