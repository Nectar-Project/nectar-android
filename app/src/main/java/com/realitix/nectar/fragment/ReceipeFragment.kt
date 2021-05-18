package com.realitix.nectar.fragment

import android.app.AlertDialog
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
import com.realitix.nectar.database.entity.ReceipeMeasure
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.database.entity.ReceipeStep
import com.realitix.nectar.database.entity.ReceipeTag
import com.realitix.nectar.databinding.FragmentReceipeAddSearchBinding
import com.realitix.nectar.databinding.FragmentReceipeBinding
import com.realitix.nectar.fragment.dialog.MeasureAddDialogFragment
import com.realitix.nectar.fragment.dialog.EditTextDialogFragment
import com.realitix.nectar.fragment.dialog.TagAddDialogFragment
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder
import com.realitix.nectar.viewmodel.ReceipeViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory


class ReceipeFragment : Fragment() {
    private var _binding: FragmentReceipeBinding? = null
    private val binding get() = _binding!!

    private lateinit var receipeUuid: String
    private val viewModel: ReceipeViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeViewModel(
                    ReceipeRepository(requireContext()),
                    ReceipeStepRepository(requireContext()),
                    ReceipeMeasureRepository(requireContext()),
                    ReceipeTagRepository(requireContext()),
                    StringKeyRepository(requireContext()),
                    StringKeyValueRepository(requireContext()),
                    MeasureRepository(requireContext()),
                    TagRepository(requireContext()),
                    receipeUuid
                )
            }
        }
    )

    private lateinit var adapterSteps: GenericAdapter<SingleLineItemViewHolder, ReceipeStep>
    private lateinit var adapterMeasures: GenericAdapter<SingleLineItemViewHolder, ReceipeMeasure>
    private lateinit var adapterTags: GenericAdapter<SingleLineItemViewHolder, ReceipeTag>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            receipeUuid = it.getString("receipeUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReceipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setupWithNavController(findNavController())

        // Set RecyclerView for Steps
        adapterSteps = GenericAdapter(
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
        binding.recyclerViewSteps.hasFixedSize()
        binding.recyclerViewSteps.adapter = adapterSteps

        // Set RecyclerView for Measures
        adapterMeasures = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, measure ->
                holder.text.text = "${measure.quantity} ${measure.measure.getName()}"
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
            }
        )
        binding.recyclerViewMeasures.hasFixedSize()
        binding.recyclerViewMeasures.adapter = adapterMeasures

        // Set RecyclerView for Tags
        adapterTags = GenericAdapter(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, tag ->
                holder.text.text = tag.tag.getName()
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
            }
        )
        binding.recyclerViewTags.hasFixedSize()
        binding.recyclerViewTags.adapter = adapterTags

        val rReceipeStep = ReceipeStepRepository(requireContext())

        viewModel.receipe.observe(viewLifecycleOwner) {
            binding.name.text = it.getName()
            binding.stars.text = it.stars.toString()
            adapterSteps.setData(it.getStepsOrdered(rReceipeStep))
            adapterMeasures.setData(it.measures)
            adapterTags.setData(it.tags)
        }


        binding.name.setOnClickListener {
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

        binding.stars.setOnClickListener {
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

        binding.recyclerViewSteps.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), binding.recyclerViewSteps, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val step = adapterSteps.getAtPosition(position)
                val action = ReceipeFragmentDirections.actionReceipeFragmentToReceipeStepFragment(step.uuid)
                findNavController().navigate(action)
            }
        }))

        binding.fab.setCallbackFirst {
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

        binding.fab.setCallbackSecond {
            MeasureAddDialogFragment(
                object:
                    MeasureAddDialogFragment.Listener {
                    override fun getOnSelect(index: Int) = viewModel.getAllMeasures()[index]
                    override fun onCreate(name: String) = viewModel.insertMeasure(name)
                    override fun onAdd(measureUuid: String, quantity: Int) = viewModel.insertReceipeMeasure(measureUuid, quantity.toFloat())
                    override fun getData(): List<String> = viewModel.getAllMeasures().map { it.getName() }
                }
            ).show(parentFragmentManager, "addReceipeMeasure")
        }

        binding.fab.setCallbackThird {
            TagAddDialogFragment(
                object:
                    TagAddDialogFragment.Listener {
                    override fun getOnSelect(index: Int) = viewModel.getAllTags()[index]
                    override fun onCreate(name: String) = viewModel.insertTag(name)
                    override fun onAdd(tagUuid: String) = viewModel.insertReceipeTag(tagUuid)
                    override fun getData(): List<String> = viewModel.getAllTags().map { it.getName() }
                }
            ).show(parentFragmentManager, "addReceipeMeasure")
        }

        binding.fab.setCallbackFour {
            AlertDialog.Builder(requireContext())
                .setMessage("Etes vous sûr de supprimer cette recette ?")
                .setCancelable(true)
                .setPositiveButton("Oui") { _, _ ->
                    viewModel.deleteReceipe(
                        ReceipeStepAlimentStateRepository(requireContext()),
                        ReceipeStepReceipeRepository(requireContext())
                    )
                    findNavController().popBackStack()
                }
                .create()
                .show()
        }
    }
}