package com.realitix.mealassistant.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R
import com.realitix.mealassistant.adapter.ReceipeStepsDataAdapter
import com.realitix.mealassistant.databinding.FragmentReceipeBinding
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.viewmodel.ReceipeStepViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory

private const val ARG_STEP_ID = "stepId"
private const val ARG_RECEIPE_ID = "receipeId"

class ReceipeStepFragment : Fragment() {
    private var stepId: Long = -1
    private var receipeId: Long = -1

    private lateinit var binding: FragmentReceipeBinding
    private val viewModel: ReceipeStepViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeStepViewModel(ReceipeRepository.getInstance(context!!), receipeId, stepId)
            }
        }
    )

    private lateinit var recyclerView: RecyclerView

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
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_receipe_step, container, false)

        // Set aliment listing
        recyclerView = binding.fragmentReceipeListSteps
        adapter = ReceipeStepsDataAdapter()
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
        return binding.root
    }
}
