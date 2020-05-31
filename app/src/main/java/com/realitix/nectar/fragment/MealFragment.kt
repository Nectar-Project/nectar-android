package com.realitix.nectar.fragment


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
import com.realitix.nectar.R
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.util.*
import com.realitix.nectar.viewmodel.MealViewModel
import com.realitix.nectar.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.fragment_meal.*


class MealFragment : Fragment() {
    private lateinit var mealUuid: String
    private val viewModel: MealViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                MealViewModel(MealRepository(requireContext()), mealUuid)
            }
        }
    )

    private lateinit var adapter: GenericAdapter<TwoLineItemViewHolder, RecyclerViewMerger>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealUuid = it.getString("mealUuid")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_meal, container, false)

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

        viewModel.meal.observe(viewLifecycleOwner) {
            adapter.setData(RecyclerViewMerger.from(it.aliments, it.receipes))
            datetime.text = NectarUtil.dayMonthYearFromTimestamp(it.timestamp)
        }

        fab.setCallbackFirst {
            val action = MealFragmentDirections.actionMealFragmentToAlimentAddSearchFragment(mealUuid, EntityType.MEAL.ordinal)
            findNavController().navigate(action)
        }

        fab.setCallbackSecond {
            val action = MealFragmentDirections.actionMealFragmentToReceipeAddFragment(mealUuid, EntityType.MEAL.ordinal)
            findNavController().navigate(action)
        }
    }
}
