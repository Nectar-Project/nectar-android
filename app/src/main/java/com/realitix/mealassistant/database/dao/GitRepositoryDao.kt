package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.realitix.mealassistant.database.entity.GitRepository
import com.realitix.mealassistant.database.entity.GitRepositoryRaw
import com.realitix.mealassistant.database.entity.ReceipeStepReceipeRaw

@Dao
interface GitRepositoryDao: BaseDao<GitRepositoryRaw> {
    @Query("SELECT * FROM GitRepositoryRaw")
    fun list(): List<GitRepository>
}