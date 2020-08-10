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
import com.realitix.nectar.repository.*
import com.realitix.nectar.viewmodel.AlimentViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import kotlinx.android.synthetic.main.fragment_aliment_nutrition.dataTable
import kotlin.reflect.full.memberProperties


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

        viewModel.aliment.observe(viewLifecycleOwner) {
            if(it.states.isNotEmpty()) {
                val header = DataTableHeader.Builder()
                    .item("Propriété", 1)
                    .item("Valeur", 1)
                    .build()
                val rows: ArrayList<DataTableRow> = arrayListOf()
                for (p in Nutrition::class.memberProperties) {
                    val n = it.states[0].nutrition
                    rows.add(
                        DataTableRow.Builder().value(p.name).value((p.get(n) as Float).toString())
                            .build()
                    )
                }

                dataTable.header = header
                dataTable.rows = rows
                dataTable.inflate(requireContext())
            }
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
