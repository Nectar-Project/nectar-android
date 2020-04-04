package com.realitix.nectar.viewmodel

import androidx.lifecycle.*
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.repository.GitRepoRepository
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.launch

class SettingsViewModel constructor(val gitRepo: GitRepoRepository) : ViewModel() {
    val gitRepositories: LiveData<List<GitRepository>> = gitRepo.listLive()

    fun insertGitRepository(r: GitRepository) {
        viewModelScope.launch {
            gitRepo.insertGitRepositorySuspend(r)
        }
    }
}