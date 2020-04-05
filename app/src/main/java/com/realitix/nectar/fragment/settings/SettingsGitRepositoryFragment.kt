package com.realitix.nectar.fragment.settings


import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.preference.*
import com.realitix.nectar.R
import com.realitix.nectar.repository.GitRepoRepository


class SettingsGitRepositoryFragment: PreferenceFragmentCompat() {
    private lateinit var uuid: String
    private lateinit var frequencyEntries: Array<String>
    private lateinit var frequencyEntryValues: Array<String>
    private lateinit var dataStore: GitRepositoryDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            uuid = it.getString("uuid")!!
        }
        frequencyEntries = resources.getStringArray(R.array.frequencyEntries)
        frequencyEntryValues = resources.getStringArray(R.array.frequencyEntryValues)
        dataStore = GitRepositoryDataStore(GitRepoRepository(requireContext()), frequencyEntryValues, uuid)
        super.onCreate(savedInstanceState)
    }

    private fun checkUrl(url: String?): String? {
        if(url.isNullOrBlank())
            return "URL non renseignée"
        if(!URLUtil.isValidUrl(url))
            return "URL invalide: $url"
        return url
    }

    private fun frequencyLabel(f: String?): String? {
        val index = frequencyEntryValues.indexOf(f)
        return frequencyEntries[index]
    }

    private fun setValueAsSummary(pref: Preference, initVal: String?, modifier: (v: String?) -> String? = {it}) {
        pref.summary = modifier(initVal)
        pref.setOnPreferenceChangeListener { preference, newValue ->
            preference.summary = modifier(newValue.toString())
            true
        }
    }

    private fun configureField(key: String, modifier: (v: String?) -> String? = {it}) {
        val pref = preferenceManager.preferenceScreen.findPreference<Preference>(key)!!
        setValueAsSummary(pref, dataStore.getString(pref.key, ""), modifier)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = dataStore
        setPreferencesFromResource(R.xml.settings_git_repository, rootKey)

        val backButton = preferenceManager.preferenceScreen.findPreference<Preference>("backButton")!!
        backButton.setOnPreferenceClickListener {
            parentFragmentManager.popBackStack()
            true
        }

        configureField("name")
        configureField("url", ::checkUrl)
        configureField("frequency", ::frequencyLabel)
        configureField("username")
        configureField("password")

        val credential = preferenceManager.preferenceScreen.findPreference<Preference>("credential")!!
        credential.setOnPreferenceChangeListener { _, newValue ->
            if(!(newValue as Boolean)) {
                val username = preferenceManager.preferenceScreen.findPreference<EditTextPreference>("username")!!
                val password = preferenceManager.preferenceScreen.findPreference<EditTextPreference>("password")!!
                username.text = ""
                username.callChangeListener("")
                password.text = ""
                password.callChangeListener("")
            }
            true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(uuid: String) =
            SettingsGitRepositoryFragment().apply {
                arguments = Bundle().apply {
                    putString("uuid", uuid)
                }
            }
    }
}
