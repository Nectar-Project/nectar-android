package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface MeasureDao: BaseDao<MeasureRaw> {
    @Transaction
    @Query("SELECT * FROM MeasureRaw WHERE uuid = :uuid")
    fun get(uuid: String): Measure?
}