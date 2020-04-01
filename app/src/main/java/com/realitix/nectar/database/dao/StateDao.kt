package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.State
import com.realitix.nectar.database.entity.StateRaw

@Dao
interface StateDao: BaseDao<StateRaw> {
    @Transaction
    @Query("SELECT * FROM StateRaw WHERE uuid = :uuid")
    fun get(uuid: String): State?
}