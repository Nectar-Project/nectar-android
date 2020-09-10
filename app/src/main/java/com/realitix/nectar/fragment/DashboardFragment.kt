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
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.ShoppingList
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
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
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.toolbar


class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                DashboardViewModel(
                    MealRepository(requireContext()),
                    ReceipeStepRepository(requireContext()),
                    ShoppingListRepository(requireContext()),
                    ShoppingListAlimentStateRepository(requireContext()),
                    NectarUtil.timestamp()
                )
            }
        }
    )

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, ShoppingList>
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Set RecyclerView
        adapter = GenericAdapter(
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
        recyclerViewShopping.hasFixedSize()
        recyclerViewShopping.adapter = adapter

        viewModel.shoppingLists.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        recyclerViewShopping.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerViewShopping, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val shoppingList = adapter.getAtPosition(position)
                val action = DashboardFragmentDirections.actionDashboardFragmentToShoppingListFragment(shoppingList.uuid)
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
        buttonAddShopping.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, picker.toString())
        }
    }
}