package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Relation
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep

@Dao
interface ReceipeDao {
    class ReceipeFull : Receipe() {
        @Relation(parentColumn = "id", entityColumn = "receipeId", entity = ReceipeStep::class)
        var steps: List<ReceipeStepDao.ReceipeStepFull>? = null
    }

    @Query("SELECT * FROM Receipe")
    fun list(): List<Receipe?>?

    @Query("SELECT * FROM Receipe WHERE name LIKE '%' ||  :search || '%'")
    fun search(search: String?): List<Receipe?>?

    @Query("SELECT * FROM Receipe WHERE id=:id")
    operator fun get(id: Long): Receipe?

    @Query("SELECT * FROM Receipe WHERE Receipe.id=:id")
    fun getFull(id: Long): ReceipeFull?

    @Insert
    fun insert(receipe: Receipe?): Long
}