package com.realitix.mealassistant.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.realitix.mealassistant.R
import com.realitix.mealassistant.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_meals.*
import java.text.SimpleDateFormat
import java.util.*


class MealsFragment : Fragment() {

    val adapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(System.currentTimeMillis()/1000, activity!!.supportFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_meals, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Set viewpager
        reloadViewPager()
        tabLayout.setupWithViewPager(viewPager)

        // Set datepicker
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        agenda.setOnClickListener {
            picker.show(activity!!.supportFragmentManager, picker.toString())
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
