package com.realitix.nectar.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.ShoppingList
import com.realitix.nectar.database.entity.ShoppingListAlimentState
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.repository.ShoppingListAlimentStateRepository
import com.realitix.nectar.repository.ShoppingListRepository
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import com.realitix.nectar.viewmodel.ShoppingListViewModel
import kotlinx.android.synthetic.main.fragment_shopping_list.*


class ShoppingListFragment : Fragment() {
    private lateinit var shoppingListUuid: String

    private val viewModel: ShoppingListViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ShoppingListViewModel(
                    ShoppingListRepository(requireContext()),
                    ShoppingListAlimentStateRepository(requireContext()),
                    shoppingListUuid
                )
            }
        }
    )

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, ShoppingListAlimentState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            shoppingListUuid = it.getString("shoppingListUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_shopping_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, shoppingListAlimentState ->
                holder.text.text = shoppingListAlimentState.alimentState.aliment.getName() + " ("+shoppingListAlimentState.alimentState.state.getName()+"): "+shoppingListAlimentState.weight+"g"
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
                when(shoppingListAlimentState.checked) {
                    false -> holder.layout.setBackgroundColor(Color.RED)
                    true -> holder.layout.setBackgroundColor(Color.GREEN)
                }

            }
        )
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        viewModel.shoppingList.observe(viewLifecycleOwner) {
            adapter.setData(it.aliments)
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val a = adapter.getAtPosition(position)

                if(a.checked) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Etes vous sûr de retirer cet article ?")
                        .setCancelable(true)
                        .setPositiveButton("Oui") { _, _ ->
                            viewModel.toggleShoppingListAlimentState(a)
                        }
                        .create()
                        .show()
                }
                else {
                    viewModel.toggleShoppingListAlimentState(a)
                }
            }
        }))
    }

}