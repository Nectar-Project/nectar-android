package com.realitix.nectar.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.realitix.nectar.R
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.viewmodel.DashboardViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.toolbar
import kotlinx.android.synthetic.main.fragment_meals.*


class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                DashboardViewModel(
                    MealRepository(requireContext()),
                    ReceipeStepRepository(requireContext()),
                    NectarUtil.timestamp()
                )
            }
        }
    )
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

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