package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.mealassistant.database.entity.*

@Dao
interface MeasureDao: BaseDao<MeasureRaw> {
    @Transaction
    @Query("SELECT * FROM MeasureRaw WHERE uuid = :uuid")
    fun get(uuid: String): Measure?
}