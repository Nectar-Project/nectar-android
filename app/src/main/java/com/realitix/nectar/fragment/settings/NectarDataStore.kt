package com.realitix.nectar.fragment.settings

import android.util.Log
import androidx.preference.PreferenceDataStore
import com.realitix.nectar.database.entity.GitCredentials
import com.realitix.nectar.database.entity.GitRepositoryRaw
import com.realitix.nectar.repository.GitRepoRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

class GitRepositoryDataStore(
    var repository: GitRepoRepository,
    private val frequencyEntryValues: Array<String>,
    uuid: String
): PreferenceDataStore() {
    val r = runBlocking { repository.getSuspend(uuid)!! }

    override fun putBoolean(key: String?, value: Boolean) {
        when(key) {
            "enabled" -> r.enabled = value
            "rescan" -> r.rescan = value
            "readOnly" -> r.readOnly = value
            "credential" -> {
                if(value) {
                    r.credentials = GitCredentials("", "")
                    r.readOnly = false
                }
                else r.credentials = null
            }
            else -> throw Exception("Can't put boolean $key")
        }
        GlobalScope.launch { repository.updateSuspend(r) }
    }

    override fun putString(key: String?, value: String?) {
        when(key) {
            "name" -> r.name = value!!
            "url" -> r.url = value!!
            "username" -> r.credentials?.username = value!!
            "password" -> r.credentials?.password = value!!
            "frequency" -> r.frequency = value!!.toLong()
            else -> throw Exception("Can't put string $key")
        }
        GlobalScope.launch { repository.updateSuspend(r) }
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return when(key) {
            "enabled" -> r.enabled
            "rescan" -> r.rescan
            "readOnly" -> r.readOnly
            "credential" -> r.credentials != null
            else -> throw Exception("Can't get boolean $key")
        }
    }

    override fun getString(key: String?, defValue: String?): String? {
        return when(key) {
            "name" -> r.name
            "url" -> r.url
            "username" -> r.credentials?.username
            "password" -> r.credentials?.password
            "frequency" -> {
                if(frequencyEntryValues.contains(r.frequency.toString()))
                    return r.frequency.toString()
                else
                    return frequencyEntryValues[0]
            }
            else -> throw Exception("Can't get string $key")
        }
    }
}