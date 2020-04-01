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
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.AlimentAddSearchViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_add_search.*


class AlimentAddSearchFragment : Fragment() {
    private lateinit var objUuid: String
    private var enumId: Int = -1

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Aliment>
    private val viewModel: AlimentAddSearchViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentAddSearchViewModel(AlimentRepository(requireContext()))
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            objUuid = it.getString("objUuid")!!
            enumId = it.getInt("enumId")
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
                    Glide.with(this).load(aliment.images[0]).into(holder.icon)
                }
                else {
                    holder.icon.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_receipt_black_36dp
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
                val action = AlimentAddSearchFragmentDirections.actionAlimentAddSearchFragmentToAlimentAddQuantityFragment(aliment.uuid, objUuid, enumId)
                findNavController().navigate(action)
            }
        }))
    }
}
