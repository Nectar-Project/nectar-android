package com.realitix.nectar.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController

import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Meal
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.MealPagerViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_meal_pager.*


class MealPagerFragment : Fragment() {
    private var timestamp: Long = -1
    private val viewModel: MealPagerViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                MealPagerViewModel(MealRepository(requireContext()), timestamp)
            }
        }
    )
    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Meal>
    private val timePicker by lazy {
        TimePickerDialog.newInstance(
            { _: TimePickerDialog, i: Int, i1: Int, _: Int ->
                val pickerTimestamp = NectarUtil.beginDayTimestamp(timestamp) + NectarUtil.hourTimestamp(i) + NectarUtil.minuteTimestamp(i1)
                val mid = viewModel.createMeal(pickerTimestamp)
                val action = MealsFragmentDirections.actionMealsFragmentToMealFragment(mid)
                findNavController().navigate(action)
            },
            0, 0, true
        )
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, meal ->
                holder.text.text = NectarUtil.hourMinuteFromTimestamp(meal.timestamp)
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
            }
        )
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        viewModel.meals.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        // Set fab
        fab.setOnClickListener {
            timePicker.show(requireActivity().supportFragmentManager, "TimePickerDialog")
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val meal = adapter.getAtPosition(position)
                val action = MealsFragmentDirections.actionMealsFragmentToMealFragment(meal.uuid)
                findNavController().navigate(action)
            }
        }))
    }


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
