package com.realitix.mealassistant.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.realitix.mealassistant.MainActivity
import com.realitix.mealassistant.R
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.viewmodel.AlimentAddQuantityViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_add_quantity.*


class AlimentAddQuantityFragment : Fragment() {
    private var alimentId: Long = -1
    private var stepId: Long = -1

    private val viewModel: AlimentAddQuantityViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentAddQuantityViewModel(
                    ReceipeRepository.getInstance(context!!),
                    AlimentRepository.getInstance(context!!),
                    alimentId, stepId
                )
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentId = it.getLong("alimentId")
            stepId = it.getLong("stepId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_aliment_add_quantity, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())

        viewModel.aliment.observe(viewLifecycleOwner) {
            alimentName.text = it.name
        }

        button.setOnClickListener {
            val quantity = Integer.parseInt(quantityInput.text.toString())
            viewModel.createReceipeStepAliment(quantity)
            (activity!! as MainActivity).toggleKeyboard()
            findNavController().popBackStack()
        }
    }
}
