package com.realitix.nectar.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.realitix.nectar.fragment.MealPagerFragment


class ViewPagerAdapter(var anchorTimestamp: Long, fm: FragmentManager):
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int  = 100
    override fun getItem(position: Int): Fragment = MealPagerFragment.newInstance(getTimestampFromPosition(position))
    override fun getPageTitle(position: Int): CharSequence = MealUtil.dayMonthFromTimestamp(getTimestampFromPosition(position))

    fun getTimestampFromPosition(position: Int): Long {
        val decalPosition = position - count/2
        val oneDay: Long = 86400
        return anchorTimestamp + decalPosition.toLong()*oneDay
    }
}