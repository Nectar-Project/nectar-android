package com.realitix.mealassistant.fragment


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.util.GenericAdapter
import com.realitix.mealassistant.util.RecyclerItemClickListener
import com.realitix.mealassistant.util.SingleLineItemViewHolder
import com.realitix.mealassistant.viewmodel.AlimentAddSearchViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_add_search.*


class AlimentAddSearchFragment : Fragment() {
    private lateinit var objUuid: String
    private var enumId: Int = -1

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Aliment>
    private val viewModel: AlimentAddSearchViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentAddSearchViewModel(AlimentRepository.getInstance(requireContext()))
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
