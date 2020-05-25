package com.realitix.nectar.fragment.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.database.entity.AlimentStateMeasure
import com.realitix.nectar.util.GenericAdapter
import com.realitix.nectar.util.RecyclerItemClickListener
import com.realitix.nectar.util.SingleLineItemViewHolder


class AlimentAddDialogFragment(
    private val aliment: Aliment,
    private val listener: Listener
): DialogFragment() {
    private lateinit var d: Dialog

    interface Listener {
        fun onClick(alimentState: AlimentState, quantity: Int)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = layoutInflater.inflate(R.layout.dialog_aliment_add, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerViewState)
        val linearLayout = dialogView.findViewById<LinearLayout>(R.id.linearLayout)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
        val button = dialogView.findViewById<Button>(R.id.button)
        val quantity = dialogView.findViewById<EditText>(R.id.quantity)

        // RecyclerView
        val adapter = GenericAdapter<SingleLineItemViewHolder, AlimentState>(
            { v: ViewGroup -> SingleLineItemViewHolder.create(v) },
            { holder, alimentState ->
                holder.text.text = alimentState.state.getName()
                holder.icon.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_receipt_black_24dp
                    )
                )
            }
        )
        recyclerView.adapter = adapter
        adapter.setData(aliment.states)
        recyclerView.hasFixedSize()

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), recyclerView, object: RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val alimentState = adapter.getAtPosition(position)
                val spinnerChoices = alimentState.measures.map { it.measure.getName() }.toMutableList()
                spinnerChoices.add("g")
                val spinnerChoicesQuantity = alimentState.measures.map { it.quantity }.toMutableList()
                spinnerChoicesQuantity.add(1)

                linearLayout.visibility = View.VISIBLE

                val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerChoices)
                spinner.adapter = spinnerAdapter

                button.setOnClickListener {
                    val computedQuantity = spinnerChoicesQuantity[spinner.selectedItemPosition]*quantity.text.toString().toFloat()
                    listener.onClick(alimentState, computedQuantity.toInt())
                    d.dismiss()
                }
            }
        }))

        d = activity?.let {
            AlertDialog.Builder(it)
                .setView(dialogView)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")

        return d
    }
}