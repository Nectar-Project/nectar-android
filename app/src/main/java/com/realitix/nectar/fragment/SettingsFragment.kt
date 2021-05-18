package com.realitix.nectar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.realitix.nectar.R
import com.realitix.nectar.databinding.FragmentReceipeStepBinding
import com.realitix.nectar.databinding.FragmentSettingsBinding
import com.realitix.nectar.fragment.settings.SettingsGitRepositoryFragment
import com.realitix.nectar.fragment.settings.SettingsInnerFragment


class SettingsFragment : Fragment(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        val newFragment = SettingsInnerFragment.newInstance()
        updateFragment(newFragment)
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        val uuid = pref!!.key
        val newFragment = SettingsGitRepositoryFragment.newInstance(uuid)
        updateFragment(newFragment)
        return true
    }
}
