package com.realitix.nectar.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.realitix.nectar.R
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.viewmodel.AlimentViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_aliment.*


class AlimentFragment : Fragment() {
    private lateinit var alimentUuid: String
    private val viewModel: AlimentViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                AlimentViewModel(
                    AlimentRepository(requireContext()),
                    alimentUuid
                )
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alimentUuid = it.getString("alimentUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_aliment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setupWithNavController(findNavController())
    }
}
