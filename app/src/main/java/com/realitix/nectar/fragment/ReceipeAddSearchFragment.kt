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
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.repository.MealReceipeRepository
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepReceipeRepository
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.ReceipeAddSearchViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe_add_search.*


class ReceipeAddSearchFragment : Fragment() {
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
    ): View? = inflater.inflate(R.layout.fragment_receipe_add_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

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

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val receipe = adapter.getAtPosition(position)

                EditTextDialogFragment(
                    "Proportion de ${receipe.getName()}",
                    object :
                        EditTextDialogFragment.OnValidateListener {
                        override fun onValidate(dialog: EditTextDialogFragment) {
                            val quantity = dialog.getText().toFloat()
                            viewModel.create(receipe.uuid, quantity)
                            findNavController().popBackStack()
                        }
                    }).show(parentFragmentManager, "addAliment")

            }
        }))
    }
}
