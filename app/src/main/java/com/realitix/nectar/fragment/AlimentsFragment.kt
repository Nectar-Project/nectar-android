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
import com.realitix.nectar.fragment.dialog.AlimentStateDialogFragment
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.fragment.view.AlimentItemViewHolder
import com.realitix.nectar.repository.*
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
    ): View? = inflater.inflate(R.layout.fragment_aliments, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbarLayout.setupWithNavController(toolbar, findNavController())

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

                holder.buttonAddState.setOnClickListener {
                    AlimentStateDialogFragment(
                        "Sélectionner un état à ajouter",
                        "Ajouter un état",
                        "Etat à créer",
                        object:
                            AlimentStateDialogFragment.Listener {
                            override fun onSelect(index: Int) = viewModel.insertAlimentState(aliment.uuid, viewModel.getAllStates()[index].uuid)
                            override fun onCreate(name: String) = viewModel.insertState(name)
                            override fun getData(): List<String> = viewModel.getAllStates().map { it.getName() }
                        }
                    ).show(parentFragmentManager, "addAlimentState")
                }

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

                holder.recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), holder.recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val alimentState = adapterStates.getAtPosition(position)
                        val action = AlimentsFragmentDirections.actionAlimentsFragmentToAlimentStateFragment(alimentState.uuid)
                        findNavController().navigate(action)
                    }
                }))
            }
        )
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        viewModel.aliments.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val viewToggle = (view as LinearLayout).getChildAt(1)
                if(viewToggle.visibility == View.GONE) {
                    viewToggle.visibility = View.VISIBLE
                }
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
                            /*val action =
                                AlimentsFragmentDirections.actionAlimentsFragmentToAlimentFragment(
                                    alimentUuid
                                )
                            findNavController().navigate(action)*/
                        }
                    })

            dialog.show(parentFragmentManager, "createAliment")
        }
    }
}
