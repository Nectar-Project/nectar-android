package com.realitix.nectar.fragment.settings

import androidx.preference.PreferenceDataStore
import com.realitix.nectar.database.entity.GitRepositoryRaw
import com.realitix.nectar.repository.GitRepoRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking

class GitRepositoryDataStore(var repository: GitRepoRepository, val uuid: String): PreferenceDataStore() {

    override fun putBoolean(key: String?, value: Boolean) {
        runBlocking {
            val r = repository.get(uuid)!!
            when(key) {
                "enabled" -> r.enabled = value
            }
            repository.updateGitRepository(r)
        }
    }
}