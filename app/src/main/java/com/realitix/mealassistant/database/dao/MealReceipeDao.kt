package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.MealReceipe
import com.realitix.mealassistant.database.entity.Receipe

@Dao
interface MealReceipeDao {
    class MealReceipeFull : MealReceipe() {
        @Relation(parentColumn = "receipeId", entityColumn = "id")
        lateinit var receipe: Receipe
    }

    @Insert
    fun insert(mealReceipe: MealReceipe): Long
}