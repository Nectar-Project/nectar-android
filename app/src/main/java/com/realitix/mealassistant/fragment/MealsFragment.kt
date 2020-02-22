package com.realitix.mealassistant.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.datepicker.MaterialDatePicker
import com.realitix.mealassistant.R
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.util.MealMath
import com.realitix.mealassistant.util.ViewPagerAdapter
import com.realitix.mealassistant.viewmodel.MealsViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_meals.*


class MealsFragment : Fragment() {
    private var timestamp: Long = -1
    private val adapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(System.currentTimeMillis()/1000, childFragmentManager)
    }
    private val timePicker by lazy {
        TimePickerDialog.newInstance(
            { _: TimePickerDialog, i: Int, i1: Int, _: Int ->
                val pickerTimestamp = MealMath.beginDayTimestamp(timestamp) + MealMath.hourTimestamp(i) + MealMath.minuteTimestamp(i1)
                val mid = viewModel.createMeal(pickerTimestamp)
                val action = MealsFragmentDirections.actionMealsFragmentToMealFragment(mid)
                findNavController().navigate(action)
            },
            0, 0, true
        )
    }
    private val viewModel: MealsViewModel by activityViewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                MealsViewModel(MealRepository.getInstance(context!!), findNavController())
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_meals, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Force viewModel creation (must be set before viewpager fragments
        viewModel

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
            picker.show(activity!!.supportFragmentManager, picker.toString())
        }
        picker.addOnPositiveButtonClickListener {
            adapter.anchorTimestamp = it/1000
            reloadViewPager()
        }

        // Set fab
        fab.setOnClickListener {
            timePicker.show(activity!!.supportFragmentManager, "TimePickerDialog")
        }
    }

    private fun reloadViewPager() {
        viewPager.adapter = adapter
        viewPager.currentItem = (viewPager.adapter)!!.count/2
    }
}
