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
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.RecyclerItemClickListener
import com.realitix.mealassistant.util.SingleLineItemViewHolder
import com.realitix.mealassistant.viewmodel.ReceipeViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe.*


class ReceipeFragment : Fragment() {
    private lateinit var receipeUuid: String
    private val viewModel: ReceipeViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeViewModel(ReceipeRepository(requireContext()), receipeUuid)
            }
        }
    )

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, ReceipeStep>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receipeUuid = it.getString("receipeUuid")!!
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
                        requireContext(),
                        R.drawable.ic_receipt_black_36dp
                    )
                )
            }
        )
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        viewModel.receipe.observe(viewLifecycleOwner) {
            name.text = it.getName()
            nbPeople.text = it.nb_people.toString()
            stars.text = it.stars.toString()
            nameTextInput.setText(it.getName())
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

        fab.setOnClickListener {
            switchStepAdd()
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val step = adapter.getAtPosition(position)
                val action = ReceipeFragmentDirections.actionReceipeFragmentToReceipeStepFragment(step.uuid, receipeUuid)
                findNavController().navigate(action)
            }
        }))
    }


    private fun switchReceipeName() {
        if(nameContainer.visibility == View.GONE) {
            name.visibility = View.INVISIBLE
            nameContainer.visibility = View.VISIBLE
            nameTextInput.requestFocus()
            (requireActivity() as MainActivity).toggleKeyboard()
        }
        else {
            name.visibility = View.VISIBLE
            nameTextInput.clearFocus()
            nameContainer.visibility = View.GONE
            (requireActivity() as MainActivity).toggleKeyboard()
        }
    }

    private fun switchStepAdd() {
        if(stepContainer.visibility == View.GONE) {
            stepContainer.visibility = View.VISIBLE
            stepTextInput.requestFocus()
            (requireActivity() as MainActivity).toggleKeyboard()
        }
        else {
            stepContainer.visibility = View.GONE
            (requireActivity() as MainActivity).toggleKeyboard()
        }
    }
}