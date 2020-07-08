package com.realitix.nectar.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.realitix.nectar.R
import com.realitix.nectar.util.NectarUtil
import kotlinx.android.synthetic.main.fragment_aliment.*
import kotlinx.android.synthetic.main.fragment_aliment.tabLayout
import kotlinx.android.synthetic.main.fragment_aliment.viewPager
import kotlinx.android.synthetic.main.fragment_aliments.*
import kotlinx.android.synthetic.main.fragment_aliments.toolbar
import kotlinx.android.synthetic.main.fragment_meals.*

class AlimentFragment : Fragment() {
    private lateinit var alimentUuid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentUuid = it.getString("alimentUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_aliment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        tabLayout.setupWithViewPager(viewPager)
        viewPager.currentItem = 1
        viewPager.adapter = AlimentPagerAdapter(childFragmentManager)
    }

    private inner class AlimentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> AlimentDetailFragment.newInstance(alimentUuid)
                1 -> AlimentNutritionFragment.newInstance(alimentUuid)
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
