package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface StringKeyDao: BaseDao<StringKeyRaw> {
    @Transaction
    @Query("SELECT * FROM StringKeyRaw WHERE uuid = :uuid")
    fun get(uuid: String): StringKey?
}