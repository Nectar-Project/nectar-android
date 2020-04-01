package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface DatabaseUpdateDao: BaseDao<DatabaseUpdateRaw> {
    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw WHERE entityUuid = :uuid")
    fun get(uuid: String): DatabaseUpdate?

    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw ORDER BY timestamp")
    fun list(): List<DatabaseUpdate>
}