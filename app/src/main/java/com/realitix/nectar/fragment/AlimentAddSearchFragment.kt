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
import com.realitix.nectar.fragment.dialog.AlimentAddDialogFragment
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.MealAlimentRepository
import com.realitix.nectar.repository.ReceipeStepAlimentRepository
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentAddSearchViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_add_search.*
import java.io.File


class AlimentAddSearchFragment : Fragment() {
    private lateinit var objUuid: String
    private lateinit var entityType: EntityType

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Aliment>
    private val viewModel: AlimentAddSearchViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentAddSearchViewModel(
                    AlimentRepository(requireContext()),
                    ReceipeStepAlimentRepository(requireContext()),
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
    ): View? = inflater.inflate(R.layout.fragment_aliment_add_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

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
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
        viewModel.aliments.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null)
                    viewModel.searchAliments(newText)
                return true
            }
        })

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val aliment = adapter.getAtPosition(position)
                val listener = object: AlimentAddDialogFragment.Listener {
                    override fun onClick(alimentState: AlimentState, quantity: Int) {
                        viewModel.create(alimentState.alimentUuid, quantity)
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
