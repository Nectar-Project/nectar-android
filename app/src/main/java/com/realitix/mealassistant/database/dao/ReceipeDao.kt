package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep

@Dao
interface ReceipeDao {
    class ReceipeFull : Receipe() {
        @Relation(parentColumn = "id", entityColumn = "receipeId", entity = ReceipeStep::class)
        var steps: List<ReceipeStepDao.ReceipeStepFull>? = null
    }

    @Query("SELECT * FROM Receipe")
    fun list(): LiveData<List<Receipe>>

    @Query("SELECT * FROM Receipe WHERE name LIKE '%' ||  :search || '%'")
    fun search(search: String): LiveData<List<Receipe>>

    @Query("SELECT * FROM Receipe WHERE id=:id")
    fun get(id: Long): LiveData<Receipe>

    @Update
    suspend fun update(receipe: Receipe)

    @Query("SELECT * FROM Receipe WHERE id=:id")
    suspend fun has(id: Long): Receipe?

    @Insert
    suspend fun insert(receipe: Receipe): Long

    @Transaction
    @Query("SELECT * FROM Receipe WHERE Receipe.id=:id")
    fun getFull(id: Long): LiveData<ReceipeFull>
}