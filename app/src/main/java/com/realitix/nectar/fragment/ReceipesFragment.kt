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
import com.realitix.nectar.databinding.FragmentReceipeBinding
import com.realitix.nectar.databinding.FragmentReceipesBinding
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.ReceipeListViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory

class ReceipesFragment : Fragment() {
    private var _binding: FragmentReceipesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReceipeListViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeListViewModel(
                    ReceipeRepository(requireContext()),
                    StringKeyRepository(requireContext()),
                    StringKeyValueRepository(requireContext())
                )
            }
        }
    )

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Receipe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReceipesBinding.inflate(inflater, container, false)
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
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adapter

        viewModel.receipes.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), binding.recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val receipe = adapter.getAtPosition(position)
                val action = ReceipesFragmentDirections.actionReceipesToSingle(receipe.uuid)
                view.findNavController().navigate(action)
            }
        }))


        binding.fab.setOnClickListener {
            EditTextDialogFragment(
                "Nom de la recette",
                object :
                    EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        val receipeUuid = viewModel.createReceipe(dialog.getText())
                        val action = ReceipesFragmentDirections.actionReceipesToSingle(receipeUuid)
                        findNavController().navigate(action)
                    }
                }).show(parentFragmentManager, "create_receipe")
        }
    }
}
