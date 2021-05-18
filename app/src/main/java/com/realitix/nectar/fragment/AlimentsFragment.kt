package com.realitix.nectar.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.database.entity.AlimentStateRaw
import com.realitix.nectar.database.entity.Nutrition
import com.realitix.nectar.databinding.FragmentAlimentAddSearchBinding
import com.realitix.nectar.databinding.FragmentAlimentsBinding
import com.realitix.nectar.fragment.dialog.AlimentStateDialogFragment
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.fragment.view.AlimentItemViewHolder
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.*
import com.realitix.nectar.viewmodel.AlimentsViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory

class AlimentsFragment : Fragment() {
    private var _binding: FragmentAlimentsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AlimentsViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentsViewModel(
                    AlimentRepository(requireContext()),
                    StateRepository(requireContext()),
                    AlimentStateRepository(requireContext()),
                    StringKeyRepository(requireContext()),
                    StringKeyValueRepository(requireContext())
                )
            }
        }
    )

    private lateinit var adapter: GenericAdapter<AlimentItemViewHolder, Aliment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlimentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.collapsingToolbarLayout.setupWithNavController(binding.toolbar, findNavController())

        // Set RecyclerView
        val rAlimentState = AlimentStateRepository(requireContext())
        adapter = GenericAdapter(
            { v: ViewGroup -> AlimentItemViewHolder.create(v) }
        ) { holder, aliment ->
            holder.text.text = aliment.getName()
            holder.icon.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_receipt_black_24dp
                )
            )

            val adapterStates = GenericAdapter<SingleLineItemViewHolder, AlimentState>(
                { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
                { holderAlimentState, alimentState ->
                    holderAlimentState.text.text = alimentState.state.getName()
                    holderAlimentState.icon.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_receipt_black_24dp
                        )
                    )
                }
            )
            holder.recyclerView.adapter = adapterStates
            adapterStates.setData(aliment.getStates(rAlimentState))

            holder.recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(
                    requireContext(),
                    holder.recyclerView,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            val alimentState = adapterStates.getAtPosition(position)
                            val action =
                                AlimentsFragmentDirections.actionAlimentsFragmentToAlimentStatePagerFragment(
                                    alimentState.uuid
                                )
                            try {
                                findNavController().navigate(action)
                            } catch (e: Exception) {
                                Toast.makeText(requireContext(), "Recharger la page", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            )

            holder.buttonAddState.setOnClickListener {
                AlimentStateDialogFragment(
                    "Sélectionner un état à ajouter",
                    "Ajouter un état",
                    "Etat à créer",
                    object :
                        AlimentStateDialogFragment.Listener {
                        override fun onSelect(index: Int) {
                            viewModel.insertAlimentState(
                                aliment.uuid,
                                viewModel.getAllStates()[index].uuid
                            ) {
                                (holder.recyclerView.adapter as GenericAdapter<SingleLineItemViewHolder, AlimentState>).addData(
                                    it
                                )
                            }

                        }

                        override fun onCreate(name: String) = viewModel.insertState(name)
                        override fun getData(): List<String> =
                            viewModel.getAllStates().map { it.getName() }
                    }
                ).show(parentFragmentManager, "addAlimentState")
            }

            holder.buttonDelete.setOnClickListener {
                viewModel.deleteAliment(aliment)
            }

            holder.buttonEdit.setOnClickListener {
                EditTextDialogFragment(
                    "Nom de l'aliment'",
                    object :
                        EditTextDialogFragment.OnValidateListener {
                        override fun onValidate(dialog: EditTextDialogFragment) {
                            viewModel.updateAlimentName(aliment, dialog.getText())
                        }
                    }, aliment.getName()
                ).show(parentFragmentManager, "updateAlimentName")
            }
        }
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adapter

        viewModel.aliments.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), binding.recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val viewToggle = (view as LinearLayout).getChildAt(1)
                if(viewToggle.visibility == View.GONE) {
                    viewToggle.visibility = View.VISIBLE
                }
            }
        }))


        binding.fab.setOnClickListener {
            val dialog =
                EditTextDialogFragment(
                    "Nom de l'aliment",
                    object :
                        EditTextDialogFragment.OnValidateListener {
                        override fun onValidate(dialog: EditTextDialogFragment) {
                            viewModel.createAliment(dialog.getText())
                        }
                    })

            dialog.show(parentFragmentManager, "createAliment")
        }
    }
}
