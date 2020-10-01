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
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.fragment.dialog.PreviousStepDialogFragment
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.*
import com.realitix.nectar.viewmodel.ReceipeStepViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe_step.*


class ReceipeStepFragment : Fragment() {
    private lateinit var stepUuid: String
    private lateinit var receipeUuid: String

    private val viewModel: ReceipeStepViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeStepViewModel(
                    ReceipeRepository(requireContext()),
                    ReceipeStepRepository(requireContext()),
                    StringKeyValueRepository(requireContext()),
                    receipeUuid,
                    stepUuid
                )
            }
        }
    )

    private lateinit var adapter: GenericAdapter<TwoLineItemViewHolder, RecyclerViewMerger>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stepUuid = it.getString("stepUuid")!!
            receipeUuid = it.getString("receipeUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = inflater.inflate(R.layout.fragment_receipe_step, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        stepDescription.setOnClickListener {
            EditTextDialogFragment(
                "Description de l'étape",
                object :
                    EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        viewModel.updateStepDescription(dialog.getText())
                    }
                }, viewModel.step.value!!.getDescription()
            ).show(parentFragmentManager, "updateStepDescription")
        }

        stepTime.setOnClickListener {
            EditTextDialogFragment(
                "Durée de l'étape",
                object: EditTextDialogFragment.OnValidateListener {
                    override fun onValidate(dialog: EditTextDialogFragment) {
                        viewModel.updateStepTime(dialog.getText().toInt())
                    }
                }, viewModel.step.value!!.duration.toString()
            ).show(parentFragmentManager, "updateStepTime")
        }

        previousStep.setOnClickListener {
            PreviousStepDialogFragment(
                "Etape précédente",
                object: PreviousStepDialogFragment.Listener {
                    override fun onSelect(index: Int) {
                        var r: ReceipeStep? = null
                        if(index > 0) {
                            r = viewModel.getAllSteps()[index-1]
                        }

                        viewModel.setPreviousStep(r)
                    }

                    override fun getData(): List<String> {
                        val r = mutableListOf<String>()
                        r.add("Aucune")
                        for(s in viewModel.getAllSteps()) {
                            r.add(s.getDescription())
                        }

                        return r
                    }
                }
            ).show(parentFragmentManager, "updatePreviousStep")
        }

        // Set RecyclerView
        adapter = GenericAdapter(
            { v: ViewGroup -> TwoLineItemViewHolder.create(v) },
            { holder, merger ->
                holder.text.text = merger.text
                holder.secondary.text = merger.secondary
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
            }
        )
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

        viewModel.receipe.observe(viewLifecycleOwner) {
            receipeName.text = it.getName()
        }

        val rReceipeStepRepository = ReceipeStepRepository(requireContext())
        viewModel.step.observe(viewLifecycleOwner) {
            stepDescription.text = it.getDescription()
            stepTime.text = it.duration.toString() + " minutes"
            previousStep.text = it.getPreviousStep(rReceipeStepRepository)?.getDescription() ?: "Aucune"
            adapter.setData(RecyclerViewMerger.from(it.aliments, it.receipes))
        }

        fab.setCallbackFirst {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToAlimentAddSearchFragment(stepUuid, EntityType.RECEIPE.ordinal)
            findNavController().navigate(action)
        }
        fab.setCallbackSecond {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToReceipeAddFragment(stepUuid, EntityType.RECEIPE.ordinal)
            findNavController().navigate(action)
        }

        buttonDelete.setOnClickListener {
            viewModel.deleteStep()
            findNavController().popBackStack()
        }
    }
}
