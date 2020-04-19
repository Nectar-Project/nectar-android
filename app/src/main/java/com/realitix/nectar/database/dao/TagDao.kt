package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class TagDao: GenericGetUuidDao<TagRaw, Tag>() {
    @Transaction
    @Query("SELECT * FROM TagRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Tag>

    @Transaction
    @Query("SELECT * FROM TagRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Tag?

    @Transaction
    @Query("SELECT * FROM TagRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Tag?

    @Transaction
    @Query("SELECT * FROM TagRaw")
    abstract override fun list(): List<Tag>

    @Transaction
    @Query("SELECT * FROM TagRaw")
    abstract override fun listLive(): LiveData<List<Tag>>

    @Transaction
    @Query("SELECT * FROM TagRaw")
    abstract override suspend fun listSuspend(): List<Tag>
}