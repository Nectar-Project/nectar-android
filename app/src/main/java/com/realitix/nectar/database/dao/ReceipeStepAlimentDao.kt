package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.realitix.nectar.database.entity.ReceipeStepAlimentRaw

@Dao
interface ReceipeStepAlimentDao {
    @Insert
    suspend fun insertSuspended(receipeStepAliment: ReceipeStepAlimentRaw)

    @Insert
    fun insert(receipeStepAliment: ReceipeStepAlimentRaw)
}