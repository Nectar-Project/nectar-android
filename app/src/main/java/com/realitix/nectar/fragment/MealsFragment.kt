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
import com.realitix.nectar.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_meals.*


class MealsFragment : Fragment() {
    private var timestamp: Long = -1
    private val adapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(System.currentTimeMillis()/1000, childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_meals, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Set viewpager
        tabLayout.setupWithViewPager(viewPager)
        reloadViewPager()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                timestamp = ((viewPager.adapter) as ViewPagerAdapter).getTimestampFromPosition(position)
            }
        })

        // Set datepicker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        agenda.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, picker.toString())
        }
        picker.addOnPositiveButtonClickListener {
            adapter.anchorTimestamp = it/1000
            reloadViewPager()
        }
    }

    private fun reloadViewPager() {
        viewPager.adapter = adapter
        viewPager.currentItem = (viewPager.adapter)!!.count/2
    }
}
