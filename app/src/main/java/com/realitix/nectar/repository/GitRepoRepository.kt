package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.database.entity.GitRepositoryRaw

class GitRepoRepository(val context: Context) {

    fun listGitRepositories(): List<GitRepository> {
        return NectarDatabase.getInstance(context).gitRepositoryDao().list()
    }

    fun listEnabled(): List<GitRepository> {
        return NectarDatabase.getInstance(context).gitRepositoryDao().listEnabled()
    }

    fun listLive(): LiveData<List<GitRepository>> {
        return NectarDatabase.getInstance(context).gitRepositoryDao().listLive()
    }

    suspend fun getSuspend(uuid: String): GitRepository? {
        return NectarDatabase.getInstance(context).gitRepositoryDao().getSuspend(uuid)
    }

    fun updateGitRepository(repo: GitRepository) {
        NectarDatabase.getInstance(context).gitRepositoryDao().update(repo)
    }

    suspend fun updateSuspend(repo: GitRepository) {
        NectarDatabase.getInstance(context).gitRepositoryDao().updateSuspend(repo)
    }

    suspend fun insertSuspend(r: GitRepositoryRaw) {
        NectarDatabase.getInstance(context).gitRepositoryDao().insertSuspend(r)
    }

    fun insert(r: GitRepositoryRaw) {
        NectarDatabase.getInstance(context).gitRepositoryDao().insert(r)
    }
}