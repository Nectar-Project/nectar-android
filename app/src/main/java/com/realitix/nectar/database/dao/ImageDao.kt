package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface ImageDao: BaseDao<ImageRaw> {
    @Transaction
    @Query("SELECT * FROM ImageRaw WHERE uuid = :uuid")
    fun get(uuid: String): Image?
}