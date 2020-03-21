package com.realitix.mealassistant.repository

import android.content.Context
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.GitRepository

class GitRepoRepository(val context: Context) {

    fun listGitRepositories(): List<GitRepository> {
        return MealDatabase.getInstance(context).gitRepositoryDao().list()
    }

    fun updateGitRepository(repo: GitRepository) {
        MealDatabase.getInstance(context).gitRepositoryDao().update(repo)
    }

    companion object {
        private var instance: GitRepoRepository? = null
        @Synchronized
        fun getInstance(context: Context): GitRepoRepository {
            if (instance == null) {
                instance = GitRepoRepository(context)
            }
            return instance as GitRepoRepository
        }
    }
}