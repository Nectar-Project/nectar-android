package com.realitix.nectar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.realitix.nectar.databinding.FragmentAlimentStatePagerBinding

class AlimentStatePagerFragment : Fragment() {
    private var _binding: FragmentAlimentStatePagerBinding? = null
    private val binding get() = _binding!!

    private lateinit var alimentStateUuid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentStateUuid = it.getString("alimentStateUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlimentStatePagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.currentItem = 1
        binding.viewPager.adapter = AlimentPagerAdapter(childFragmentManager)
    }

    private inner class AlimentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> AlimentStatePagerDetailFragment.newInstance(alimentStateUuid)
                1 -> AlimentStatePagerNutritionFragment.newInstance(alimentStateUuid)
                else -> throw Exception("No fragment specified")
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when(position) {
                0 -> "Détail"
                1 -> "Nutrition"
                else -> throw Exception("No fragment specified")
            }
        }
    }
}
