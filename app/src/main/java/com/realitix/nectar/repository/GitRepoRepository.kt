package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.GitRepository

class GitRepoRepository(val context: Context) {

    fun listGitRepositories(): List<GitRepository> {
        return NectarDatabase.getInstance(context).gitRepositoryDao().list()
    }

    fun listLive(): LiveData<List<GitRepository>> {
        return NectarDatabase.getInstance(context).gitRepositoryDao().listLive()
    }

    fun updateGitRepository(repo: GitRepository) {
        NectarDatabase.getInstance(context).gitRepositoryDao().update(repo)
    }
}