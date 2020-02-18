package com.realitix.mealassistant.fragment


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
import com.realitix.mealassistant.MainActivity
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.GenericAdapter
import com.realitix.mealassistant.util.RecyclerItemClickListener
import com.realitix.mealassistant.util.SingleLineItemViewHolder
import com.realitix.mealassistant.viewmodel.ReceipeAddSearchViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe_add_search.*


class ReceipeAddFragment : Fragment() {
    private var stepId: Long = -1

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, Receipe>
    private val viewModel: ReceipeAddSearchViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeAddSearchViewModel(ReceipeRepository.getInstance(context!!), stepId)
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stepId = it.getLong("stepId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_receipe_add_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, receipe ->
                holder.text.text = receipe.name
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
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

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText != null)
                    viewModel.searchReceipes(newText)
                return true
            }
        })

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context!!, recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val receipe = adapter.getAtPosition(position)
                viewModel.createReceipeStepReceipe(receipe.id)
                (activity!! as MainActivity).toggleKeyboard()
                findNavController().popBackStack()
            }
        }))
    }
}
