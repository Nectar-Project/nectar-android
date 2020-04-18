package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class BookImageDao: BaseDao<BookImageRaw, BookImage>() {
    @Transaction
    @Query("SELECT * FROM BookImageRaw WHERE bookUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<BookImage>

    @Transaction
    @Query("SELECT * FROM BookImageRaw WHERE bookUuid = :uuid")
    abstract override fun get(uuid: String): BookImage?

    @Transaction
    @Query("SELECT * FROM BookImageRaw WHERE bookUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): BookImage?

    @Transaction
    @Query("SELECT * FROM BookImageRaw")
    abstract override fun list(): List<BookImage>

    @Transaction
    @Query("SELECT * FROM BookImageRaw")
    abstract override fun listLive(): LiveData<List<BookImage>>

    @Transaction
    @Query("SELECT * FROM BookImageRaw")
    abstract override suspend fun listSuspend(): List<BookImage>
}