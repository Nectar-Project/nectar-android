package com.realitix.nectar.fragment.settings


import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.repository.GitRepoRepository
import com.realitix.nectar.util.UuidGenerator
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import com.realitix.nectar.viewmodel.SettingsViewModel


class SettingsInnerFragment: PreferenceFragmentCompat() {
    private val viewModel: SettingsViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                SettingsViewModel(GitRepoRepository(requireContext()))
            }
        }
    )

    private lateinit var gitCategory: PreferenceCategory

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        val context = preferenceManager.context
        val screen = preferenceManager.createPreferenceScreen(context)

        gitCategory = PreferenceCategory(context)
        gitCategory.key = "git_category"
        gitCategory.title = "Dépôts"
        gitCategory.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_git_icon_black_24dp)
        gitCategory.icon.setTint(resources.getColor(R.color.colorIconPreferenceCategory))
        screen.addPreference(gitCategory)

        preferenceScreen = screen
    }

    private fun addRepositoryPreference(): Preference {
        val p = EditTextPreference(context)
        p.key = "addGitRepository"
        p.title = "Créer un nouveau dépôt"
        p.dialogTitle = "Ajout d'un dépôt"
        p.dialogMessage = "Donnez un nom à ce nouveau dépôt"
        p.dialogIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_black_24dp)
        p.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_black_24dp)
        p.icon.setTint(resources.getColor(R.color.colorIconPreferenceCategory))
        p.negativeButtonText = "Annuler"
        p.positiveButtonText = "Valider"

        p.setOnPreferenceChangeListener { _, newValue ->
            viewModel.insertGitRepository(GitRepository(
                UuidGenerator().generateUuid(),
                newValue as String,
                url = "",
                enabled = false,
                rescan = true,
                readOnly = true,
                lastCheck = 0,
                frequency = 60*60, // One hour
                credentials = null
            ))
            false
        }

        return p
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gitRepositories.observe(viewLifecycleOwner) {
            // remove all preferences
            gitCategory.removeAll()

            // add all existing repositories
            for(git in it) {
                val pref = Preference(context)
                pref.key= git.uuid
                pref.title = git.name
                pref.fragment = SettingsGitRepositoryFragment::class.qualifiedName
                pref.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_navigate_next_black_24dp)
                pref.icon.setBounds(10, 10, 10, 10)
                gitCategory.addPreference(pref)
            }

            // add the add preference
            gitCategory.addPreference(addRepositoryPreference())
        }
    }

    override fun getCallbackFragment(): Fragment {
        return requireParentFragment()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsInnerFragment()
            .apply {}
    }
}
