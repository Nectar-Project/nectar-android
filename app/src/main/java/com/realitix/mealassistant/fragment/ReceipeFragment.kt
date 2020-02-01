package com.realitix.mealassistant.fragment

import android.animation.LayoutTransition
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.realitix.mealassistant.R
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

    // Views
    private lateinit var receipeName: MaterialTextView
    private lateinit var receipeEditName: TextInputEditText
    private lateinit var receipeEditNameContainer: TextInputLayout

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

        // Set views
        receipeName = binding.receipeName
        receipeEditName = binding.receipeEditName
        receipeEditNameContainer = binding.receipeEditNameContainer

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.receipe.observe(viewLifecycleOwner) {
            binding.receipe = it
        }

        receipeName.setOnClickListener {
            zoomReceipeEditName()
        }

        //val bbar = activity!!.findViewById<BottomAppBar>(R.id.bottom_app_bar)
        //val ll = activity!!.findViewById<LinearLayout>(R.id.bottom_app_bar_linear_layout)
        //val fab = activity!!.findViewById<FloatingActionButton>(R.id.fab)
        //fab.hide()

        //ll.getChildAt(2).animate().alpha(0.5f)

        //ll.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        //ll.setPadding(ll.paddingLeft, ll.paddingTop, ll.paddingRight + 200, ll.paddingBottom)
        //ll.getChildAt(2).visibility = View.GONE
        //ll.weightSum = 4f
        //bbar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
    }

    private fun zoomReceipeEditName() {
        receipeName.visibility = View.INVISIBLE
        receipeEditNameContainer.visibility = View.VISIBLE
    }
}