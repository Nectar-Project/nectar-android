package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.MealAliment
import com.realitix.mealassistant.database.entity.MealAlimentRaw

@Dao
interface MealAlimentDao {
    @Insert
    suspend fun insert(mealAliment: MealAlimentRaw): Long
}