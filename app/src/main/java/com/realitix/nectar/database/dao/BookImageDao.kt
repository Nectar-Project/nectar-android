package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class BookImageDao: GenericGetJoinDao<BookImageRaw, BookImage>() {
    @Transaction
    @Query("SELECT * FROM BookImageRaw WHERE bookUuid = :uuid1 AND imageUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<BookImage>

    @Transaction
    @Query("SELECT * FROM BookImageRaw WHERE bookUuid = :uuid1 AND imageUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): BookImage?

    @Transaction
    @Query("SELECT * FROM BookImageRaw WHERE bookUuid = :uuid1 AND imageUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): BookImage?

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