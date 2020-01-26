package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStepReceipe

@Dao
interface ReceipeStepReceipeDao {
    class ReceipeStepReceipeFull : ReceipeStepReceipe() {
        @Relation(parentColumn = "receipeId", entityColumn = "id")
        var receipeDetail: Receipe? = null
    }

    @Insert
    fun insert(receipeStepReceipe: ReceipeStepReceipe?): Long
}