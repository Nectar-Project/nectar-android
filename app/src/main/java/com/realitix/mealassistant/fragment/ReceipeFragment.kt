package com.realitix.mealassistant.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.realitix.mealassistant.MainActivity
import com.realitix.mealassistant.R
import com.realitix.mealassistant.adapter.ReceipeStepsDataAdapter
import com.realitix.mealassistant.databinding.FragmentReceipeBinding
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.RecyclerItemClickListener
import com.realitix.mealassistant.viewmodel.ReceipeViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


private const val ARG_RECEIPE_ID = "receipeId"


class ReceipeFragment : Fragment() {
    private var receipeId: Long = -1
    private lateinit var binding: FragmentReceipeBinding
    private val viewModel: ReceipeViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeViewModel(ReceipeRepository.getInstance(context!!), receipeId)
            }
        }
    )

    private lateinit var name: MaterialTextView
    private lateinit var editName: EditText
    private lateinit var nbPeople: TextView
    private lateinit var stars: TextView
    private lateinit var adapter: ReceipeStepsDataAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receipeId = it.getLong(ARG_RECEIPE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_receipe, container, false)

        name = binding.fragmentReceipeReceipeName
        nbPeople = binding.fragmentReceipeReceipeNbPeople
        stars = binding.fragmentReceipeReceipeStar

        // Set steps listing
        recyclerView = binding.fragmentReceipeListSteps
        adapter = ReceipeStepsDataAdapter()
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.receipe.observe(viewLifecycleOwner) {
            binding.receipe = it
            binding.receipeEditName.setText(it.name)
            adapter.setSteps(it.steps)
        }

        name.setOnClickListener {
            switchReceipeName()
        }

        binding.receipeEditNameButton.setOnClickListener {
            val newName: String = binding.receipeEditName.text.toString()
            switchReceipeName()
            viewModel.updateReceipeName(newName)
        }

        binding.fragmentReceipeAddStepButton.setOnClickListener {
            val description: String = binding.fragmentReceipeAddStep.text.toString()
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

        configureFab()
    }

    private fun configureFab() {
        activity!!.fab.setOnClickListener {
            switchStepAdd()
        }
    }

    private fun switchReceipeName() {
        if(binding.receipeEditNameContainer.visibility == View.GONE) {
            name.visibility = View.INVISIBLE
            binding.receipeEditNameContainer.visibility = View.VISIBLE
            binding.receipeEditName.requestFocus()
            (activity!! as MainActivity).toggleKeyboard()
        }
        else {
            name.visibility = View.VISIBLE
            binding.receipeEditName.clearFocus()
            binding.receipeEditNameContainer.visibility = View.GONE
            (activity!! as MainActivity).toggleKeyboard()
        }
    }

    private fun switchStepAdd() {
        if(binding.fragmentReceipeAddStepContainer.visibility == View.GONE) {
            binding.fragmentReceipeAddStepContainer.visibility = View.VISIBLE
            binding.fragmentReceipeAddStep.requestFocus()
            (activity!! as MainActivity).toggleKeyboard()
        }
        else {
            binding.fragmentReceipeAddStepContainer.visibility = View.GONE
            (activity!! as MainActivity).toggleKeyboard()
        }
    }
}