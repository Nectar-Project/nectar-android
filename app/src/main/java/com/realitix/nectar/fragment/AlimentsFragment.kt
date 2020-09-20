package com.realitix.nectar.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.fragment.view.AlimentItemViewHolder
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.AlimentStateRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.util.TwoLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentsViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliments.*

class AlimentsFragment : Fragment() {
    private val viewModel: AlimentsViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentsViewModel(
                    AlimentRepository(requireContext()),
                    StringKeyRepository(requireContext()),
                    StringKeyValueRepository(requireContext())
                )
            }
        }
    )

    private lateinit var adapter: GenericAdapter<AlimentItemViewHolder, Aliment>
    private lateinit var adapterStates: GenericAdapter<SingleLineItemViewHolder, AlimentState>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_aliments, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbarLayout.setupWithNavController(toolbar, findNavController())

        // Adapter for state recyclerview
        adapterStates = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, alimentState ->
                holder.text.text = alimentState.state.getName()
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
            }
        )
        // Set RecyclerView
        val rAlimentState = AlimentStateRepository(requireContext())
        adapter = GenericAdapter(
            { v: ViewGroup -> AlimentItemViewHolder.create(v) },
            { holder, aliment ->
                holder.text.text = aliment.getName()
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
                holder.recyclerView.adapter = adapterStates
                adapterStates.setData(aliment.getStates(rAlimentState))
            }
        )
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        viewModel.aliments.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                (view as RelativeLayout).getChildAt(2).visibility = View.GONE
                /*val aliment = adapter.getAtPosition(position)
                val action = AlimentsFragmentDirections.actionAlimentsFragmentToAlimentFragment(aliment.uuid)
                view.findNavController().navigate(action)*/
            }
        }))


        fab.setOnClickListener {
            val dialog =
                EditTextDialogFragment(
                    "Nom de l'aliment",
                    object :
                        EditTextDialogFragment.OnValidateListener {
                        override fun onValidate(dialog: EditTextDialogFragment) {
                            val alimentUuid = viewModel.createAliment(dialog.getText())
                            val action =
                                AlimentsFragmentDirections.actionAlimentsFragmentToAlimentFragment(
                                    alimentUuid
                                )
                            findNavController().navigate(action)
                        }
                    })

            dialog.show(parentFragmentManager, "createAliment")
        }
    }
}
