package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.mealassistant.database.entity.AlimentState
import com.realitix.mealassistant.database.entity.AlimentStateRaw
import com.realitix.mealassistant.database.entity.AlimentTagRaw
import com.realitix.mealassistant.database.entity.StateRaw


@Dao
interface AlimentStateDao: BaseDao<AlimentStateRaw> {
    @Transaction
    @Query("SELECT * FROM AlimentStateRaw WHERE uuid = :uuid")
    fun getLive(uuid: String): LiveData<AlimentState>

    @Transaction
    @Query("SELECT * FROM AlimentStateRaw WHERE uuid = :uuid")
    fun get(uuid: String): AlimentState?
}