package com.realitix.nectar.fragment

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
import com.realitix.nectar.R
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.ReceipeViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe.*


class ReceipeFragment : Fragment() {
    private lateinit var receipeUuid: String
    private val viewModel: ReceipeViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeViewModel(
                    ReceipeRepository(requireContext()),
                    ReceipeStepRepository(requireContext()),
                    StringKeyRepository(requireContext()),
                    StringKeyValueRepository(requireContext()),
                    receipeUuid
                )
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
                holder.text.text = step.getDescription()
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

        viewModel.receipe.observe(viewLifecycleOwner) {
            name.text = it.getName()
            portions.text = it.portions.toString()
            stars.text = it.stars.toString()
            adapter.setData(it.steps)
        }

        name.setOnClickListener {
            EditTextDialogFragment(
                "Nom de la recette",
                object :
                    EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        viewModel.updateReceipeName(dialog.getText())
                    }
                }, viewModel.receipe.value!!.getName()
            ).show(parentFragmentManager, "updateReceipeName")
        }

        portions.setOnClickListener {
            EditTextDialogFragment(
                "Nombre de portions",
                object :
                    EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        viewModel.updateReceipePortions(dialog.getText().toInt())
                    }
                }, viewModel.receipe.value!!.portions.toString()
            ).show(parentFragmentManager, "updateReceipePortions")
        }

        stars.setOnClickListener {
            EditTextDialogFragment(
                "Nombre d'étoile",
                object :
                    EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        viewModel.updateReceipeStars(dialog.getText().toInt())
                    }
                }, viewModel.receipe.value!!.stars.toString()
            ).show(parentFragmentManager, "updateReceipeStars")
        }

        fab.setCallbackFirst {
            EditTextDialogFragment(
                "Nom de l'étape à créer",
                object :
                    EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        viewModel.insertStep(dialog.getText())
                    }
                }
            ).show(parentFragmentManager, "createStep")
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val step = adapter.getAtPosition(position)
                val action = ReceipeFragmentDirections.actionReceipeFragmentToReceipeStepFragment(step.uuid, receipeUuid)
                findNavController().navigate(action)
            }
        }))
    }
}