package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.Meal
import com.realitix.mealassistant.database.entity.MealAliment
import com.realitix.mealassistant.database.entity.MealReceipe

@Dao
interface MealDao {
    class MealFull : Meal() {
        @Relation(parentColumn = "id", entityColumn = "mealId", entity = MealAliment::class)
        var aliments: List<MealAlimentDao.MealAlimentFull>? = null
        @Relation(parentColumn = "id", entityColumn = "mealId", entity = MealReceipe::class)
        var receipes: List<MealReceipeDao.MealReceipeFull>? = null
    }

    @Query("SELECT * FROM Meal WHERE timestamp BETWEEN :begin AND :end")
    fun search(begin: Long, end: Long): List<Meal?>?

    @Insert
    fun insert(meal: Meal?): Long

    @Query("SELECT * FROM Meal WHERE id=:id")
    operator fun get(id: Long): Meal?

    @Query("SELECT * FROM Meal WHERE id=:id")
    fun getFull(id: Long): MealFull?
}