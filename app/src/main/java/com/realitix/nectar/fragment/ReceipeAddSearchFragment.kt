package com.realitix.nectar.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Receipe
import com.realitix.nectar.databinding.FragmentMealsBinding
import com.realitix.nectar.databinding.FragmentReceipeAddSearchBinding
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.fragment.dialog.ReceipeAddDialogFragment
import com.realitix.nectar.repository.MealReceipeRepository
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepReceipeRepository
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.ReceipeAddSearchViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory


class ReceipeAddSearchFragment : Fragment() {
    private var _binding: FragmentReceipeAddSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var objUuid: String
    private lateinit var entityType: EntityType

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Receipe>
    private val viewModel: ReceipeAddSearchViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeAddSearchViewModel(
                    ReceipeRepository(requireContext()),
                    ReceipeStepReceipeRepository(requireContext()),
                    MealReceipeRepository(requireContext()),
                    objUuid, entityType
                )
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            objUuid = it.getString("objUuid")!!
            entityType = EntityType.values()[it.getInt("enumId")]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReceipeAddSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

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

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null)
                    viewModel.searchReceipes(newText)
                return true
            }
        })

        binding.recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), binding.recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val receipe = adapter.getAtPosition(position)

                val listener = object: ReceipeAddDialogFragment.Listener {
                    override fun onClick(portions: Float) {
                        viewModel.create(receipe.uuid, portions)
                        findNavController().popBackStack()
                    }
                }

                ReceipeAddDialogFragment(
                    receipe,
                    listener
                ).show(parentFragmentManager, "addReceipe")
            }
        }))
    }
}
