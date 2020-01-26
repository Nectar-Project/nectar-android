package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.database.entity.ReceipeStepAliment
import com.realitix.mealassistant.database.entity.ReceipeStepReceipe

@Dao
interface ReceipeStepDao {
    class ReceipeStepFull : ReceipeStep() {
        @Relation(parentColumn = "id", entityColumn = "stepId", entity = ReceipeStepAliment::class)
        var aliments: List<ReceipeStepAlimentDao.ReceipeStepAlimentFull>? = null
        @Relation(parentColumn = "id", entityColumn = "stepId", entity = ReceipeStepReceipe::class)
        var receipes: List<ReceipeStepReceipeDao.ReceipeStepReceipeFull>? = null
    }

    @Query("SELECT * FROM ReceipeStep WHERE id=:id")
    operator fun get(id: Long): ReceipeStep?

    @Query("SELECT * FROM ReceipeStep WHERE id=:id")
    fun getFull(id: Long): ReceipeStepFull?

    @Insert
    fun insert(receipeStep: ReceipeStep?): Long
}