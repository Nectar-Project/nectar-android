package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.database.entity.GitRepositoryRaw

@Dao
interface GitRepositoryDao: BaseDao<GitRepositoryRaw> {
    @Query("SELECT * FROM GitRepositoryRaw WHERE uuid=:uuid")
    fun get(uuid: String): GitRepository?

    @Query("SELECT * FROM GitRepositoryRaw WHERE uuid=:uuid")
    suspend fun getSuspend(uuid: String): GitRepository?

    @Query("SELECT * FROM GitRepositoryRaw")
    fun list(): List<GitRepository>

    @Query("SELECT * FROM GitRepositoryRaw WHERE enabled=1")
    fun listEnabled(): List<GitRepository>

    @Query("SELECT * FROM GitRepositoryRaw")
    fun listLive(): LiveData<List<GitRepository>>
}