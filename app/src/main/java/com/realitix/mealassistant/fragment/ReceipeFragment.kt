package com.realitix.mealassistant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.realitix.mealassistant.MainActivity
import com.realitix.mealassistant.R
import com.realitix.mealassistant.util.GenericAdapter
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.SingleLineItemViewHolder
import com.realitix.mealassistant.viewmodel.ReceipeViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe.*


private const val ARG_RECEIPE_ID = "receipeId"


class ReceipeFragment : Fragment() {
    private var receipeId: Long = -1
    private val viewModel: ReceipeViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeViewModel(ReceipeRepository.getInstance(context!!), receipeId)
            }
        }
    )

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, ReceipeStepDao.ReceipeStepFull>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receipeId = it.getLong(ARG_RECEIPE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_receipe, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, step ->
                holder.text.text = step.description
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

        viewModel.receipe.observe(viewLifecycleOwner) {
            name.text = it.name
            nbPeople.text = it.nb_people.toString()
            stars.text = it.stars.toString()
            nameTextInput.setText(it.name)
            adapter.setData(it.steps!!)
        }

        name.setOnClickListener {
            switchReceipeName()
        }

        nameButton.setOnClickListener {
            val newName: String = nameTextInput.text.toString()
            switchReceipeName()
            viewModel.updateReceipeName(newName)
        }

        stepButton.setOnClickListener {
            val description: String = stepTextInput.text.toString()
            switchStepAdd()
            viewModel.createStep(description)
        }

        /*recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context!!, recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val receipe = adapter.getReceipeAtPosition(position)
                val action = ReceipeListFragmentDirections.actionReceipelistToSingle(receipe.id)
                view.findNavController().navigate(action)
            }

            override fun onLongItemClick(view: View, position: Int) {
            }
        }))*/

    }


    private fun switchReceipeName() {
        if(nameContainer.visibility == View.GONE) {
            name.visibility = View.INVISIBLE
            nameContainer.visibility = View.VISIBLE
            nameTextInput.requestFocus()
            (activity!! as MainActivity).toggleKeyboard()
        }
        else {
            name.visibility = View.VISIBLE
            nameTextInput.clearFocus()
            nameContainer.visibility = View.GONE
            (activity!! as MainActivity).toggleKeyboard()
        }
    }

    private fun switchStepAdd() {
        if(stepContainer.visibility == View.GONE) {
            stepContainer.visibility = View.VISIBLE
            stepTextInput.requestFocus()
            (activity!! as MainActivity).toggleKeyboard()
        }
        else {
            stepContainer.visibility = View.GONE
            (activity!! as MainActivity).toggleKeyboard()
        }
    }
}