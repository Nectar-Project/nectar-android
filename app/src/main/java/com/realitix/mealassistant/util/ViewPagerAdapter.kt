package com.realitix.mealassistant.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.realitix.mealassistant.fragment.MealPagerFragment
import java.text.SimpleDateFormat
import java.util.*


class ViewPagerAdapter(var anchorTimestamp: Long, fm: FragmentManager):
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int  = 100

    override fun getItem(position: Int): Fragment {
        val fragment = MealPagerFragment.newInstance(getTimestampFromPosition(position))
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        val date = Date(getTimestampFromPosition(position)*1000)
        val pattern = "EEEE d MMMM"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    private fun getTimestampFromPosition(position: Int): Long {
        val decalPosition = position - count/2
        val oneDay: Long = 86400
        return anchorTimestamp + decalPosition.toLong()*oneDay
    }
}