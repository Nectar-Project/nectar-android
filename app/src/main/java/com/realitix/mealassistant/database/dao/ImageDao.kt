package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.mealassistant.database.entity.*

@Dao
interface ImageDao: BaseDao<ImageRaw> {
    @Transaction
    @Query("SELECT * FROM ImageRaw WHERE uuid = :uuid")
    fun get(uuid: String): Image?
}