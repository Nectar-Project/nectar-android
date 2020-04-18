package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class DatabaseUpdateDao: BaseDao<DatabaseUpdateRaw, DatabaseUpdate>() {
    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw WHERE entityUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<DatabaseUpdate>

    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw WHERE entityUuid = :uuid")
    abstract override fun get(uuid: String): DatabaseUpdate?

    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw WHERE entityUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): DatabaseUpdate?

    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw")
    abstract override fun list(): List<DatabaseUpdate>

    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw")
    abstract override fun listLive(): LiveData<List<DatabaseUpdate>>

    @Transaction
    @Query("SELECT * FROM DatabaseUpdateRaw")
    abstract override suspend fun listSuspend(): List<DatabaseUpdate>
}