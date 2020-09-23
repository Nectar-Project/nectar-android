package com.realitix.nectar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.lifecycle.observe

import com.realitix.nectar.R
import com.realitix.nectar.database.entity.AlimentStateMeasure
import com.realitix.nectar.fragment.dialog.MeasureAddDialogFragment
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentStateViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_state.*


class AlimentStatePagerDetailFragment: Fragment() {
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
    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, AlimentStateMeasure>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentStateUuid = it.getString("alimentStateUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_aliment_state, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, alimentStateMeasure ->
                holder.text.text = alimentStateMeasure.measure.getName() + ":" + alimentStateMeasure.quantity+"g"
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

        viewModel.alimentState.observe(viewLifecycleOwner) {
            adapter.setData(it.measures)
        }

        fab.setOnClickListener {
            MeasureAddDialogFragment(
                object:
                    MeasureAddDialogFragment.Listener {
                    override fun getOnSelect(index: Int) = viewModel.getAllMeasures()[index]
                    override fun onCreate(name: String) = viewModel.insertMeasure(name)
                    override fun onAdd(measureUuid: String, quantity: Int) = viewModel.insertAlimentStateMeasure(measureUuid, quantity)
                    override fun getData(): List<String> = viewModel.getAllMeasures().map { it.getName() }
                }
            ).show(parentFragmentManager, "addAlimentState")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(alimentStateUuid: String) =
            AlimentStatePagerDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("alimentStateUuid", alimentStateUuid)
                }
            }
    }
}
