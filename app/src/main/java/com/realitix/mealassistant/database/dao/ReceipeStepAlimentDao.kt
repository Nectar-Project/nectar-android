package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.ReceipeStepAliment
import com.realitix.mealassistant.database.entity.ReceipeStepAlimentRaw

@Dao
interface ReceipeStepAlimentDao {
    @Insert
    suspend fun insert(receipeStepAliment: ReceipeStepAlimentRaw): Long
}