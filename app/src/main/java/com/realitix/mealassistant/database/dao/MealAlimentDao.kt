package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.MealAliment

@Dao
interface MealAlimentDao {
    class MealAlimentFull : MealAliment() {
        @Relation(parentColumn = "alimentId", entityColumn = "id")
        lateinit var aliment: Aliment
    }

    @Insert
    suspend fun insert(mealAliment: MealAliment): Long
}