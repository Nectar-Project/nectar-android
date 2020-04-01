package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface TagDao: BaseDao<TagRaw> {
    @Transaction
    @Query("SELECT * FROM TagRaw WHERE uuid = :uuid")
    fun get(uuid: String): Tag?
}