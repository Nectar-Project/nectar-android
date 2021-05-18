package com.realitix.nectar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.lifecycle.observe

import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Nutrition
import com.realitix.nectar.databinding.FragmentAlimentNutritionBinding
import com.realitix.nectar.databinding.FragmentAlimentStatePagerBinding
import com.realitix.nectar.repository.*
import com.realitix.nectar.viewmodel.AlimentStateViewModel
import com.realitix.nectar.viewmodel.AlimentViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import kotlin.reflect.full.memberProperties


class AlimentStatePagerNutritionFragment : Fragment() {
    private var _binding: FragmentAlimentNutritionBinding? = null
    private val binding get() = _binding!!

    private lateinit var alimentStateUuid: String
    private val viewModel: AlimentStateViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentStateViewModel(
                    AlimentStateRepository(requireContext()),
                    AlimentStateMeasureRepository(requireContext()),
                    MeasureRepository(requireContext()),
                    StringKeyRepository(requireContext()),
                    StringKeyValueRepository(requireContext()),
                    alimentStateUuid
                )
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentStateUuid = it.getString("alimentStateUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlimentNutritionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.alimentState.observe(viewLifecycleOwner) {
            val header = DataTableHeader.Builder()
                .item("Propriété", 1)
                .item("Valeur", 1)
                .build()
            val rows: ArrayList<DataTableRow> = arrayListOf()
            for (p in Nutrition::class.memberProperties) {
                val n = it.nutrition
                rows.add(
                    DataTableRow.Builder().value(p.name).value((p.get(n) as Float).toString())
                        .build()
                )
            }

            binding.dataTable.header = header
            binding.dataTable.rows = rows
            binding.dataTable.inflate(requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(alimentStateUuid: String) =
            AlimentStatePagerNutritionFragment().apply {
                arguments = Bundle().apply {
                    putString("alimentStateUuid", alimentStateUuid)
                }
            }
    }
}
