package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class ImageDao: GenericGetUuidDao<ImageRaw, Image>() {
    @Transaction
    @Query("SELECT * FROM ImageRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Image>

    @Transaction
    @Query("SELECT * FROM ImageRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Image?

    @Transaction
    @Query("SELECT * FROM ImageRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Image?

    @Transaction
    @Query("SELECT * FROM ImageRaw")
    abstract override fun list(): List<Image>

    @Transaction
    @Query("SELECT * FROM ImageRaw")
    abstract override fun listLive(): LiveData<List<Image>>

    @Transaction
    @Query("SELECT * FROM ImageRaw")
    abstract override suspend fun listSuspend(): List<Image>
}