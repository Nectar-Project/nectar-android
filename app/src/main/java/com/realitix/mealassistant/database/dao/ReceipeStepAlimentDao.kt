package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.database.entity.ReceipeStepAliment

@Dao
interface ReceipeStepAlimentDao {
    class ReceipeStepAlimentFull : ReceipeStepAliment() {
        @Relation(parentColumn = "alimentId", entityColumn = "id")
        var alimentDetail: Aliment? = null
    }

    @Insert
    fun insert(receipeStepAliment: ReceipeStepAliment?): Long
}