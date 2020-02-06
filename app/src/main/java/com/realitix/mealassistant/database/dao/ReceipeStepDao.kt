package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.mealassistant.database.entity.Receipe
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

    @Transaction
    @Query("SELECT * FROM ReceipeStep WHERE id=:id")
    fun getFull(id: Long): LiveData<ReceipeStepFull>

    @Query("SELECT * FROM ReceipeStep WHERE id=:id")
    suspend fun has(id: Long): ReceipeStep?

    @Insert
    suspend fun insert(receipeStep: ReceipeStep): Long
}