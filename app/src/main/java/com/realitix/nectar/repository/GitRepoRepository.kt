package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.MealDatabase
import com.realitix.nectar.database.entity.GitRepository

class GitRepoRepository(val context: Context) {

    fun listGitRepositories(): List<GitRepository> {
        return MealDatabase.getInstance(context).gitRepositoryDao().list()
    }

    fun updateGitRepository(repo: GitRepository) {
        MealDatabase.getInstance(context).gitRepositoryDao().update(repo)
    }
}