package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface BookDao: BaseDao<BookRaw> {
    @Transaction
    @Query("SELECT * FROM BookRaw WHERE uuid = :uuid")
    fun get(uuid: String): Book?
}