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
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.util.*
import com.realitix.nectar.viewmodel.ReceipeStepViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe_step.*


class ReceipeStepFragment : Fragment() {

    private lateinit var stepUuid: String
    private lateinit var receipeUuid: String
    private var isFabRotated: Boolean = false

    private val viewModel: ReceipeStepViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeStepViewModel(ReceipeRepository(requireContext()), receipeUuid, stepUuid)
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

        viewModel.step.observe(viewLifecycleOwner) {
            stepDescription.text = it.description
            adapter.setData(RecyclerViewMerger.from(it.aliments, it.receipes))
        }

        fab.setOnClickListener {
            isFabRotated = FabAnimation.rotate(it, !isFabRotated)
            if(isFabRotated) {
                FabAnimation.show(containerFabAliment)
                FabAnimation.show(containerFabReceipe)
            }
            else {
                FabAnimation.hide(containerFabAliment)
                FabAnimation.hide(containerFabReceipe)
            }
        }

        fabAliment.setOnClickListener {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToAlimentAddSearchFragment(stepUuid, MealReceipeEnum.RECEIPE)
            findNavController().navigate(action)
        }

        fabReceipe.setOnClickListener {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToReceipeAddFragment(stepUuid, MealReceipeEnum.RECEIPE)
            findNavController().navigate(action)
        }
    }
}
