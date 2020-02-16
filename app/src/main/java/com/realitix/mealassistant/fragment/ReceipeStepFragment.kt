package com.realitix.mealassistant.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.dao.ReceipeStepAlimentDao
import com.realitix.mealassistant.database.entity.Aliment
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.GenericAdapter
import com.realitix.mealassistant.util.SingleLineItemViewHolder
import com.realitix.mealassistant.viewmodel.ReceipeStepViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_receipe_step.*

private const val ARG_STEP_ID = "stepId"
private const val ARG_RECEIPE_ID = "receipeId"

class ReceipeStepFragment : Fragment() {
    private var stepId: Long = -1
    private var receipeId: Long = -1

    private val viewModel: ReceipeStepViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeStepViewModel(ReceipeRepository.getInstance(context!!), receipeId, stepId)
            }
        }
    )

    private lateinit var adapter: GenericAdapter<SingleLineItemViewHolder, ReceipeStepAlimentDao.ReceipeStepAlimentFull>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stepId = it.getLong(ARG_STEP_ID)
            receipeId = it.getLong(ARG_RECEIPE_ID)
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
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, aliment ->
                holder.text.text = aliment.aliment.name
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
            receipeName.text = it.name
        }

        viewModel.step.observe(viewLifecycleOwner) {
            stepDescription.text = it.description
            adapter.setData(it.aliments!!)
        }

        fab.setOnClickListener {
            val action = ReceipeStepFragmentDirections.actionReceipeStepFragmentToAlimentAddSearchFragment()
            findNavController().navigate(action)
        }
    }
}
