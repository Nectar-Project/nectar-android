package com.realitix.nectar.fragment


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.databinding.FragmentAlimentAddSearchBinding
import com.realitix.nectar.fragment.dialog.AlimentAddDialogFragment
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.MealAlimentRepository
import com.realitix.nectar.repository.ReceipeStepAlimentStateRepository
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentAddSearchViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import java.io.File


class AlimentAddSearchFragment : Fragment() {
    private var _binding: FragmentAlimentAddSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var objUuid: String
    private lateinit var entityType: EntityType

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Aliment>
    private val viewModel: AlimentAddSearchViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentAddSearchViewModel(
                    AlimentRepository(requireContext()),
                    ReceipeStepAlimentStateRepository(requireContext()),
                    MealAlimentRepository(requireContext()),
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
        _binding = FragmentAlimentAddSearchBinding.inflate(inflater, container, false)
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
            { holder, aliment ->
                holder.text.text = aliment.getName()
                if(aliment.images.isNotEmpty()) {
                    Glide
                        .with(this)
                        .load(File(aliment.images[0].image.path))
                        .into(holder.icon)
                }
                else {
                    holder.icon.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_receipt_black_24dp
                        )
                    )
                }
            }
        )
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adapter
        viewModel.aliments.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null)
                    viewModel.searchAliments(newText)
                return true
            }
        })

        binding.recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), binding.recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val aliment = adapter.getAtPosition(position)
                val listener = object: AlimentAddDialogFragment.Listener {
                    override fun onClick(alimentState: AlimentState, quantity: Int) {
                        viewModel.create(alimentState.uuid, quantity)
                        findNavController().popBackStack()
                    }
                }

                AlimentAddDialogFragment(
                    aliment,
                    listener
                ).show(parentFragmentManager, "addAliment")
            }
        }))
    }
}
