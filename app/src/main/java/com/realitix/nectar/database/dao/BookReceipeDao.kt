package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class BookReceipeDao: GenericGetJoinDao<BookReceipeRaw, BookReceipe>() {
    @Transaction
    @Query("SELECT * FROM BookReceipeRaw WHERE bookUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<BookReceipe>

    @Transaction
    @Query("SELECT * FROM BookReceipeRaw WHERE bookUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): BookReceipe?

    @Transaction
    @Query("SELECT * FROM BookReceipeRaw WHERE bookUuid = :uuid1 AND receipeUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): BookReceipe?

    @Transaction
    @Query("SELECT * FROM BookReceipeRaw")
    abstract override fun list(): List<BookReceipe>

    @Transaction
    @Query("SELECT * FROM BookReceipeRaw")
    abstract override fun listLive(): LiveData<List<BookReceipe>>

    @Transaction
    @Query("SELECT * FROM BookReceipeRaw")
    abstract override suspend fun listSuspend(): List<BookReceipe>
}