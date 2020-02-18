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
import com.realitix.mealassistant.util.GenericAdapter
import com.realitix.mealassistant.util.TwoLineItemViewHolder
import com.realitix.mealassistant.viewmodel.ReceipeStepViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe_step.*


class ReceipeStepFragment : Fragment() {
    class RecyclerViewMerger(var text: String, var secondary: String)

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
            val mergedList = ArrayList<RecyclerViewMerger>(it.aliments!!.size + it.receipes!!.size)
            for (a in it.aliments!!) {
                mergedList.add(RecyclerViewMerger(a.aliment.name, a.quantity.toString()+"g"))
            }
            for (r in it.receipes!!) {
                mergedList.add(RecyclerViewMerger(r.receipe.name, "test"))
            }

            adapter.setData(mergedList)
        }

        fab.setOnClickListener {
            isFabRotated = rotateFab(it, !isFabRotated)
            if(isFabRotated) {
                showIn(containerFabAliment)
                showIn(containerFabReceipe)
            }
            else {
                showOut(containerFabAliment)
                showOut(containerFabReceipe)
            }
        }

        fabAliment.setOnClickListener {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToAlimentAddSearchFragment(stepId)
            findNavController().navigate(action)
        }

        fabReceipe.setOnClickListener {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToReceipeAddFragment(stepId)
            findNavController().navigate(action)
        }
    }

    private fun rotateFab(v: View, rotate: Boolean): Boolean {
        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                // Must be set to avoid glitch
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .rotation(if (rotate) 135f else 0f)
        return rotate
    }

    private fun showIn(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 0f
        v.translationY = v.height.toFloat()
        v.animate()
            .setDuration(200)
            .translationY(0f)
            .setListener(object : AnimatorListenerAdapter() {
                // Must be set to avoid glitch
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1f)
            .start()
    }

    private fun showOut(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 1f
        v.translationY = 0f
        v.animate()
            .setDuration(200)
            .translationY(v.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.visibility = View.INVISIBLE
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }
}
