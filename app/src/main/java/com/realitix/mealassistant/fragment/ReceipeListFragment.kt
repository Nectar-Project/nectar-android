package com.realitix.mealassistant.fragment

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.realitix.mealassistant.R
import com.realitix.mealassistant.adapter.ReceipeDataAdapter
import com.realitix.mealassistant.databinding.FragmentReceipeListBinding
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.RecyclerItemClickListener
import com.realitix.mealassistant.viewmodel.ReceipesViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReceipeListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReceipeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceipeListFragment : Fragment() {
    private val viewModel: ReceipesViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipesViewModel(ReceipeRepository.getInstance(context!!))
            }
        }
    )

    private lateinit var adapter: ReceipeDataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentReceipeListBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_receipe_list, container, false)

        recyclerView = binding.listReceipes
        //button_add = binding.buttonAddReceipe
        adapter = ReceipeDataAdapter()

        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.receipes.observe(viewLifecycleOwner) {
            adapter.setReceipes(it)
        }

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context!!, recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                fab.hide()
                val receipe = adapter.getReceipeAtPosition(position)
                val action = ReceipeListFragmentDirections.actionReceipelistToSingle(receipe.id)
                view.findNavController().navigate(action)
            }

            override fun onLongItemClick(view: View, position: Int) {
            }

        }))

        val navController = findNavController()
        fab = activity!!.fab
        fab.show()
        fab.setOnClickListener {
            val action = ReceipeListFragmentDirections.actionReceipelistToSingle(-1)
            navController.navigate(action)
        }

        //val bbar = activity!!.findViewById<BottomAppBar>(R.id.bottom_app_bar)
        //val ll = activity!!.findViewById<LinearLayout>(R.id.bottom_app_bar_linear_layout)
        //val fab = activity!!.findViewById<FloatingActionButton>(R.id.fab)


        //ll.getChildAt(2).animate().alpha(0f)

        //ll.layoutTransition.enableTransitionType(LayoutTransition.CHANGE_APPEARING)
        //ll.getChildAt(2).visibility = View.INVISIBLE
        //ll.weightSum = 5f
    }
}
