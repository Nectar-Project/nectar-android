package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.repository.GitRepoRepository
import kotlinx.coroutines.launch

class SettingsViewModel constructor(val gitRepo: GitRepoRepository) : ViewModel() {
    val gitRepositories: LiveData<List<GitRepository>> = gitRepo.listLive()

    fun insertGitRepository(r: GitRepository) {
        viewModelScope.launch {
            gitRepo.insertSuspend(r)
        }
    }
}