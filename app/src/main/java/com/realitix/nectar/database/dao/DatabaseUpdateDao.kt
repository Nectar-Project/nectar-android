package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class DatabaseUpdateDao: GenericCrudDao<DatabaseUpdateRaw, DatabaseUpdate>() {
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