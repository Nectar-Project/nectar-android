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
}