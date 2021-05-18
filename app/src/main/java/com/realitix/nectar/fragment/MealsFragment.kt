package com.realitix.nectar.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.datepicker.MaterialDatePicker
import com.realitix.nectar.R
import com.realitix.nectar.databinding.FragmentMealPagerBinding
import com.realitix.nectar.databinding.FragmentMealsBinding
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.ViewPagerAdapter


class MealsFragment : Fragment() {
    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!

    private var timestamp: Long = -1
    private val adapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(NectarUtil.timestamp(), childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        // Set viewpager
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        reloadViewPager()

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                timestamp = ((binding.viewPager.adapter) as ViewPagerAdapter).getTimestampFromPosition(position)
            }
        })

        // Set datepicker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        binding.agenda.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, picker.toString())
        }
        picker.addOnPositiveButtonClickListener {
            adapter.anchorTimestamp = it/1000
            reloadViewPager()
        }
    }

    private fun reloadViewPager() {
        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = (binding.viewPager.adapter)!!.count/2
    }
}
