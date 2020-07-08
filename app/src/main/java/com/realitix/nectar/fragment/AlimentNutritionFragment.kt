package com.realitix.nectar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.lifecycle.observe

import com.realitix.nectar.R
import com.realitix.nectar.repository.*
import com.realitix.nectar.viewmodel.AlimentViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import kotlinx.android.synthetic.main.fragment_aliment_nutrition.dataTable


class AlimentNutritionFragment : Fragment() {
    private lateinit var alimentUuid: String
    private val viewModel: AlimentViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentViewModel(
                    AlimentRepository(requireContext()),
                    StateRepository(requireContext()),
                    AlimentStateRepository(requireContext()),
                    StringKeyRepository(requireContext()),
                    StringKeyValueRepository(requireContext()),
                    alimentUuid
                )
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentUuid = it.getString("alimentUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_aliment_nutrition, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val header = DataTableHeader.Builder()
            .item("Nom", 1)
            .item("Valeur", 1)
            .build()
        val rows = arrayListOf(
            DataTableRow.Builder().value("Test").value("Toto").build(),
            DataTableRow.Builder().value("Test").value("Toto").build(),
            DataTableRow.Builder().value("Test").value("Toto").build()
        )

        dataTable.header = header
        dataTable.rows = rows
        dataTable.inflate(requireContext())

        viewModel.aliment.observe(viewLifecycleOwner) {
            //name.text = it.getName()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(alimentUuid: String) =
            AlimentNutritionFragment().apply {
                arguments = Bundle().apply {
                    putString("alimentUuid", alimentUuid)
                }
            }
    }
}
