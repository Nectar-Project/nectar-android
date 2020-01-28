package com.realitix.mealassistant.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.databinding.FragmentReceipeBinding
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.viewmodel.ReceipeViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
        binding.receipe = Receipe("Une recette de test", 1, 2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.receipe.observe(viewLifecycleOwner) {
            binding.receipe = it
        }
    }
}
