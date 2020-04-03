package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.database.entity.GitRepositoryRaw

@Dao
interface GitRepositoryDao: BaseDao<GitRepositoryRaw> {
    @Query("SELECT * FROM GitRepositoryRaw")
    fun list(): List<GitRepository>

    @Query("SELECT * FROM GitRepositoryRaw")
    fun listLive(): LiveData<List<GitRepository>>
}