package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.database.entity.GitRepositoryRaw

@Dao
abstract class GitRepositoryDao: BaseDao<GitRepositoryRaw, GitRepository>() {
    @Query("SELECT * FROM GitRepositoryRaw WHERE name=:name")
    abstract fun getByName(name: String): GitRepository?

    @Query("SELECT * FROM GitRepositoryRaw WHERE enabled=1")
    abstract fun listEnabled(): List<GitRepository>

    @Transaction
    @Query("SELECT * FROM GitRepositoryRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<GitRepository>

    @Transaction
    @Query("SELECT * FROM GitRepositoryRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): GitRepository?

    @Transaction
    @Query("SELECT * FROM GitRepositoryRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): GitRepository?

    @Transaction
    @Query("SELECT * FROM GitRepositoryRaw")
    abstract override fun list(): List<GitRepository>

    @Transaction
    @Query("SELECT * FROM GitRepositoryRaw")
    abstract override fun listLive(): LiveData<List<GitRepository>>

    @Transaction
    @Query("SELECT * FROM GitRepositoryRaw")
    abstract override suspend fun listSuspend(): List<GitRepository>
}