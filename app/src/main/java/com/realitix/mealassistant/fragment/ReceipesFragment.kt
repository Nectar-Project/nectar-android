package com.realitix.mealassistant.fragment

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
import com.realitix.mealassistant.R
import com.realitix.mealassistant.util.GenericAdapter
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.RecyclerItemClickListener
import com.realitix.mealassistant.util.SingleLineItemViewHolder
import com.realitix.mealassistant.viewmodel.ReceipeListViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
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
                        R.drawable.ic_receipt_black_36dp
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
