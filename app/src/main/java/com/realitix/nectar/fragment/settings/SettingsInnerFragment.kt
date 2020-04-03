package com.realitix.nectar.fragment.settings


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.preference.*
import com.realitix.nectar.repository.GitRepoRepository
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
        screen.addPreference(gitCategory)

        preferenceScreen = screen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gitRepositories.observe(viewLifecycleOwner) {
            for(git in it) {
                gitCategory.removeAll()
                val pref = Preference(context)
                pref.key= git.name
                pref.title = git.name
                pref.fragment = SettingsGitRepositoryFragment::class.qualifiedName
                gitCategory.addPreference(pref)
            }
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
