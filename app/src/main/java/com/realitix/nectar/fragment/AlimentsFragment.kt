package com.realitix.nectar.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
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

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Aliment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =inflater.inflate(R.layout.fragment_aliments, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbarLayout.setupWithNavController(toolbar, findNavController())

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, aliment ->
                holder.text.text = aliment.getName()
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

        viewModel.aliments.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val receipe = adapter.getAtPosition(position)
                val action = ReceipesFragmentDirections.actionReceipesToSingle(receipe.uuid)
                view.findNavController().navigate(action)
            }
        }))


        fab.setOnClickListener {
            val dialog = NameDialogFragment( object: NameDialogFragment.OnValidateListener {
                override fun onValidate(dialog: NameDialogFragment) {
                    val alimentUuid = viewModel.createAliment(dialog.getName())
                    //val action = ReceipesFragmentDirections.actionReceipesToSingle(receipeUuid)
                    //findNavController().navigate(action)
                }
            })

            dialog.show(parentFragmentManager, "createAliment")
        }
    }
}
