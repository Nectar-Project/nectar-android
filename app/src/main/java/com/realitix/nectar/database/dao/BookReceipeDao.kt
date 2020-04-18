package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class BookReceipeDao: BaseDao<BookReceipeRaw, BookReceipe>() {
    @Transaction
    @Query("SELECT * FROM BookReceipeRaw WHERE bookUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<BookReceipe>

    @Transaction
    @Query("SELECT * FROM BookReceipeRaw WHERE bookUuid = :uuid")
    abstract override fun get(uuid: String): BookReceipe?

    @Transaction
    @Query("SELECT * FROM BookReceipeRaw WHERE bookUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): BookReceipe?

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