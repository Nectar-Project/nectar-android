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
import com.realitix.nectar.databinding.FragmentAlimentAddSearchBinding
import com.realitix.nectar.databinding.FragmentAlimentStateBinding
import com.realitix.nectar.fragment.dialog.MeasureAddDialogFragment
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentStateViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory


class AlimentStatePagerDetailFragment: Fragment() {
    private var _binding: FragmentAlimentStateBinding? = null
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
    ): View? {
        _binding = FragmentAlimentStateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adapter

        viewModel.alimentState.observe(viewLifecycleOwner) {
            adapter.setData(it.measures)
        }

        binding.fab.setOnClickListener {
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
