package com.realitix.mealassistant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R
import com.realitix.mealassistant.adapter.ReceipeDataAdapter
import com.realitix.mealassistant.databinding.FragmentReceipeListBinding
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.RecyclerItemClickListener
import com.realitix.mealassistant.viewmodel.ReceipeListViewModel
import com.realitix.mealassistant.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReceipeListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReceipeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReceipeListFragment : Fragment() {
    private val viewModel: ReceipeListViewModel by viewModels(
        factoryProducer = {
            RepositoryViewModelFactory {
                ReceipeListViewModel(ReceipeRepository.getInstance(context!!))
            }
        }
    )

    private lateinit var adapter: ReceipeDataAdapter
    private lateinit var recyclerView: RecyclerView

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
                val receipe = adapter.getReceipeAtPosition(position)
                val action = ReceipeListFragmentDirections.actionReceipelistToSingle(receipe.id)
                view.findNavController().navigate(action)
            }

            override fun onLongItemClick(view: View, position: Int) {
            }
        }))

        val navController = findNavController()
        val fab = activity!!.fab
        fab.show()
        fab.setOnClickListener {
            val action = ReceipeListFragmentDirections.actionReceipelistToSingle(-1)
            navController.navigate(action)
        }
    }
}
