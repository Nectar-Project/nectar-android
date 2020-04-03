package com.realitix.nectar.fragment.settings


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.preference.*
import com.realitix.nectar.R


class SettingsGitRepositoryFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        val backButton = Preference(context)
        backButton.key = "backButton"
        backButton.title = "Back"
        backButton.summary = "Come back to general settings"
        backButton.icon = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_arrow_back_black_24dp
        )

        backButton.setOnPreferenceClickListener {
            parentFragmentManager.popBackStack()
            true
        }
        screen.addPreference(backButton)

        val category = PreferenceCategory(context)
        category.key = "category"
        category.title = "Informations sur le dépôt"
        screen.addPreference(category)

        val enabled = SwitchPreference(context)
        enabled.key = "enabled"
        enabled.title = "Activé"
        enabled.summaryOn = "Le dépôt est activé"
        enabled.summaryOff = "Le dépôt est désactivé"

        val name = EditTextPreference(context)
        name.key = "name"
        name.title = "Name"
        name.text = "name"
        name.summary = "Nom du dépôt"

        val readOnly = SwitchPreference(context)
        readOnly.key = "readOnly"
        readOnly.title = "Lecture seule"
        readOnly.summaryOn = "Le dépôt est en lecture seule"
        readOnly.summaryOff = "Le dépôt est en lecture/écriture"

        val url = EditTextPreference(context)
        url.key = "url"
        url.title = "Url"
        url.text = ""
        url.summary = "Url du dépôt"

        val credential = SwitchPreference(context)
        credential.key = "credential"
        credential.title = "Identifiants de connexion"
        credential.summaryOn = "Ce dépôt est accessible via vos identifiants"
        credential.summaryOff = "Ce dépôt est acessible publiquement"

        val username = EditTextPreference(context)
        username.key = "username"
        username.title = "Nom d'utilisateur"
        username.text = ""
        username.summary = "Nom d'utilisateur permettant de se connecter à ce dépôt"

        val password = EditTextPreference(context)
        password.key = "password"
        password.title = "Mot de passe"
        password.text = ""
        password.summary = "Mot de passe permettant de se connecter à ce dépôt"

        // Add preferences to the category
        category.addPreference(enabled)
        category.addPreference(name)
        category.addPreference(readOnly)
        category.addPreference(url)
        category.addPreference(credential)
        category.addPreference(username)
        category.addPreference(password)

        screen.addPreference(category)
        preferenceScreen = screen

        // Set dependancies
        username.dependency = credential.key
        password.dependency = credential.key
        credential.dependency = enabled.key
        url.dependency = enabled.key
        readOnly.dependency = enabled.key
        name.dependency = enabled.key
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsGitRepositoryFragment()
                .apply {}
    }
}
