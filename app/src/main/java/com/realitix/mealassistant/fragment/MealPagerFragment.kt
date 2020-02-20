package com.realitix.mealassistant.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.realitix.mealassistant.R


class MealPagerFragment : Fragment() {
    private var timestamp: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            timestamp = it.getLong("timestamp")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_meal_pager, container, false)


    companion object {
        @JvmStatic
        fun newInstance(timestamp: Long) =
            MealPagerFragment().apply {
                arguments = Bundle().apply {
                    putLong("timestamp", timestamp)
                }
            }
    }
}
