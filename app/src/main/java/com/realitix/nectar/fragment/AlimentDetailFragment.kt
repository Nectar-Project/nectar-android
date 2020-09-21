package com.realitix.nectar.fragment

import android.os.Bundle
import android.util.Log
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
import com.realitix.nectar.fragment.dialog.AlimentStateDialogFragment
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_detail.*


class AlimentDetailFragment : Fragment() {
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
    ): View? = inflater.inflate(R.layout.fragment_aliment_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val rAlimentState = AlimentStateRepository(requireContext())
        viewModel.aliment.observe(viewLifecycleOwner) {
            name.text = it.getName()
            adapter.setData(it.getStates(rAlimentState))
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val alimentState = adapter.getAtPosition(position)
                //val action = AlimentDetailFragmentDirections.actionAlimentDetailFragmentToAlimentStateFragment(alimentState.uuid)
                //findNavController().navigate(action)
            }
        }))

        name.setOnClickListener {
            EditTextDialogFragment(
                "Nom de l'aliment'",
                object :
                    EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        viewModel.updateAlimentName(dialog.getText())
                    }
                }, viewModel.aliment.value!!.getName()
            ).show(parentFragmentManager, "updateAlimentName")
        }

        fab.setOnClickListener {
            AlimentStateDialogFragment(
                "Sélectionner un état à ajouter",
                "Ajouter un état",
                "Etat à créer",
                object:
                    AlimentStateDialogFragment.Listener {
                    override fun onSelect(index: Int) = viewModel.insertAlimentState(viewModel.getAllStates()[index].uuid)
                    override fun onCreate(name: String) = viewModel.insertState(name)
                    override fun getData(): List<String> = viewModel.getAllStates().map { it.getName() }
                }
            ).show(parentFragmentManager, "addAlimentState")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(alimentUuid: String) =
            AlimentDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("alimentUuid", alimentUuid)
                }
            }
    }
}
