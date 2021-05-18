package com.realitix.nectar.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.database.entity.ShoppingList
import com.realitix.nectar.databinding.FragmentAlimentNutritionBinding
import com.realitix.nectar.databinding.FragmentDashboardBinding
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.repository.ShoppingListAlimentStateRepository
import com.realitix.nectar.repository.ShoppingListRepository
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.DashboardViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                DashboardViewModel(
                    MealRepository(requireContext()),
                    ReceipeStepRepository(requireContext()),
                    ShoppingListRepository(requireContext()),
                    ShoppingListAlimentStateRepository(requireContext()),
                    NectarUtil.timestampTimezoned()
                )
            }
        }
    )

    private lateinit var adapterShopping: GenericAdapter<SingleLineItemViewHolder, ShoppingList>
    private lateinit var adapterSteps: GenericAdapter<SingleLineItemViewHolder, Pair<Long, ReceipeStep>>
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        // Set Shopping list
        adapterShopping = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, shoppingList ->
                holder.text.text = NectarUtil.dayMonthFromTimestamp(shoppingList.beginTimestamp) + " - " + NectarUtil.dayMonthFromTimestamp(shoppingList.endTimestamp)
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
            }
        )
        binding.recyclerViewShopping.hasFixedSize()
        binding.recyclerViewShopping.adapter = adapterShopping

        viewModel.shoppingLists.observe(viewLifecycleOwner) {
            adapterShopping.setData(it)
        }

        binding.recyclerViewShopping.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), binding.recyclerViewShopping, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val shoppingList = adapterShopping.getAtPosition(position)
                val action = DashboardFragmentDirections.actionDashboardFragmentToShoppingListFragment(shoppingList.uuid)
                view.findNavController().navigate(action)
            }
        }))

        // Set receipe steps
        adapterSteps = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, step ->
                holder.text.text = "Dans " + (step.first-NectarUtil.timestampTimezoned()) / 60 + " min : " + step.second.description.getValue()
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
                Log.e("ttt", step.first.toString() + "  " + NectarUtil.timestamp())
            }
        )
        binding.recyclerViewSteps.hasFixedSize()
        binding.recyclerViewSteps.adapter = adapterSteps
        adapterSteps.setData(viewModel.computeSteps())

        binding.recyclerViewSteps.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), binding.recyclerViewSteps, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val rStep = adapterSteps.getAtPosition(position)
                val action = DashboardFragmentDirections.actionDashboardFragmentToReceipeStepFragment(rStep.second.uuid)
                view.findNavController().navigate(action)
            }
        }))

        // Button
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            val begin = it.first!!/1000
            val end = it.second!!/1000
            viewModel.computeShoppingList(begin, end)
        }
        binding.buttonAddShopping.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, picker.toString())
        }
    }
}