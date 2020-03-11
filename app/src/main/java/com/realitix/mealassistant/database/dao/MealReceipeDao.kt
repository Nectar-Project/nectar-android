package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.MealReceipe
import com.realitix.mealassistant.database.entity.MealReceipeRaw
import com.realitix.mealassistant.database.entity.Receipe

@Dao
interface MealReceipeDao {
    @Insert
    suspend fun insert(mealReceipe: MealReceipeRaw): Long
}