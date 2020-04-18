package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.database.entity.GitRepositoryRaw

class GitRepoRepository(val context: Context): GenericRepository<GitRepositoryRaw, GitRepository>() {
    override fun getDao() = NectarDatabase.getInstance(context).gitRepositoryDao()
    fun listEnabled(): List<GitRepository> = getDao().listEnabled()
    fun getByName(name: String): GitRepository? = getDao().getByName(name)
}