package com.realitix.nectar.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceFragmentCompat
import com.realitix.nectar.MainActivity
import com.realitix.nectar.R
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.viewmodel.AlimentAddQuantityViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_add_quantity.*


class SettingsInnerFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}
