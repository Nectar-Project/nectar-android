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
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.fragment.dialog.ItemsDialogFragment
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment.*


class AlimentFragment : Fragment() {
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

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, AlimentState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentUuid = it.getString("alimentUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_aliment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Set RecyclerView
        adapter = GenericAdapter(
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
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        viewModel.aliment.observe(viewLifecycleOwner) {
            name.text = it.getName()
            adapter.setData(it.states)
        }

        val test = EditTextDialogFragment(
            "Nom de l'état à créer",
            object :
                EditTextDialogFragment.OnValidateListener {
                override fun onValidate(dialog: EditTextDialogFragment) {
                    viewModel.insertState(dialog.getText())
                }
            }
        )

        fab.setOnClickListener {
            val states = viewModel.getAllStates()
            ItemsDialogFragment(
                "Sélectionner un état à ajouter",
                states.map { it.getName() },
                object:
                    ItemsDialogFragment.OnSelectListener {
                    override fun onSelect(index: Int) {
                        val state = states[index]
                        viewModel.insertAlimentState(state.uuid)
                    }

                    override fun onValidate() {
                        EditTextDialogFragment(
                            "Nom de l'état à créer",
                            object :
                                EditTextDialogFragment.OnValidateListener {
                                override fun onValidate(dialog: EditTextDialogFragment) {
                                    viewModel.insertState(dialog.getText())
                                }
                            }
                        ).show(parentFragmentManager, "insertState")
                    }
                },
                "Ajouter un état"
            ).show(parentFragmentManager, "addAlimentState")
        }
    }
}
