package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class BookDao: BaseDao<BookRaw, Book>() {
    @Transaction
    @Query("SELECT * FROM BookRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Book>

    @Transaction
    @Query("SELECT * FROM BookRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Book?

    @Transaction
    @Query("SELECT * FROM BookRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Book?

    @Transaction
    @Query("SELECT * FROM BookRaw")
    abstract override fun list(): List<Book>

    @Transaction
    @Query("SELECT * FROM BookRaw")
    abstract override fun listLive(): LiveData<List<Book>>

    @Transaction
    @Query("SELECT * FROM BookRaw")
    abstract override suspend fun listSuspend(): List<Book>
}