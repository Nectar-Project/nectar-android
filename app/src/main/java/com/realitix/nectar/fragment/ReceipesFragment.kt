package com.realitix.nectar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.realitix.nectar.R
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.ReceipeListViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipes.*

class ReceipesFragment : Fragment() {
    private val viewModel: ReceipeListViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeListViewModel(ReceipeRepository(requireContext()))
            }
        }
    )

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Receipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_receipes, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbarLayout.setupWithNavController(toolbar, findNavController())

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, receipe ->
                holder.text.text = receipe.getName()
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

        viewModel.receipes.observe(viewLifecycleOwner) {
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
            val receipeUuid = viewModel.createReceipe()
            val action = ReceipesFragmentDirections.actionReceipesToSingle(receipeUuid)
            findNavController().navigate(action)
        }
    }
}
