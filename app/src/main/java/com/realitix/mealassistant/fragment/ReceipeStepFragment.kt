package com.realitix.mealassistant.fragment


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
import com.realitix.mealassistant.R
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.*
import com.realitix.mealassistant.viewmodel.ReceipeStepViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe_step.*


class ReceipeStepFragment : Fragment() {

    private var stepId: Long = -1
    private var receipeId: Long = -1
    private var isFabRotated: Boolean = false

    private val viewModel: ReceipeStepViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeStepViewModel(ReceipeRepository.getInstance(context!!), receipeId, stepId)
            }
        }
    )

    private lateinit var adapter: GenericAdapter<TwoLineItemViewHolder, RecyclerViewMerger>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stepId = it.getLong("stepId")
            receipeId = it.getLong("receipeId")
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
                        context!!,
                        R.drawable.ic_receipt_black_36dp
                    )
                )
            }
        )
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

        viewModel.receipe.observe(viewLifecycleOwner) {
            receipeName.text = it.name
        }

        viewModel.step.observe(viewLifecycleOwner) {
            stepDescription.text = it.description
            adapter.setData(RecyclerViewMerger.from(it.aliments!!, it.receipes!!))
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
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToAlimentAddSearchFragment(stepId, MealReceipeEnum.RECEIPE)
            findNavController().navigate(action)
        }

        fabReceipe.setOnClickListener {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToReceipeAddFragment(stepId)
            findNavController().navigate(action)
        }
    }
}
