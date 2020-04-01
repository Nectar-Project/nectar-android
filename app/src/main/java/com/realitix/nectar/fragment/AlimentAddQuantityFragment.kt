package com.realitix.nectar.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.realitix.nectar.MainActivity
import com.realitix.nectar.R
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.viewmodel.AlimentAddQuantityViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment_add_quantity.*


class AlimentAddQuantityFragment : Fragment() {
    private lateinit var alimentUuid: String
    private lateinit var objUuid: String
    private var enumId: Int = -1

    private val viewModel: AlimentAddQuantityViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentAddQuantityViewModel(
                    ReceipeRepository(requireContext()),
                    MealRepository(requireContext()),
                    AlimentRepository(requireContext()),
                    alimentUuid, objUuid, enumId
                )
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentUuid = it.getString("alimentUuid")!!
            objUuid = it.getString("objUuid")!!
            enumId = it.getInt("enumId")
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
            alimentName.text = it.getName()
        }

        button.setOnClickListener {
            val quantity = Integer.parseInt(quantityInput.text.toString())
            viewModel.create(quantity)
            (requireActivity() as MainActivity).toggleKeyboard()
            findNavController().popBackStack()
        }
    }
}
