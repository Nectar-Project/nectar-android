package com.realitix.nectar.fragment.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Receipe


class ReceipeAddDialogFragment(
    private val receipe: Receipe,
    private val listener: Listener
): DialogFragment() {
    private lateinit var d: Dialog

    interface Listener {
        fun onClick(portions: Float)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = layoutInflater.inflate(R.layout.dialog_receipe_add, null)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinner)
        val button = dialogView.findViewById<Button>(R.id.button)
        val quantity = dialogView.findViewById<EditText>(R.id.quantity)

        // spinner
        val spinnerChoices = receipe.measures.map { it.measure.getName() }.toMutableList()
        spinnerChoices.add("proportion")
        val spinnerChoicesQuantity = receipe.measures.map { it.quantity }.toMutableList()
        spinnerChoicesQuantity.add(1f)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerChoices)
        spinner.adapter = spinnerAdapter

        button.setOnClickListener {
            val computedQuantity = quantity.text.toString().toFloat() / spinnerChoicesQuantity[spinner.selectedItemPosition]
            listener.onClick(computedQuantity)
            d.dismiss()
        }

        d = activity?.let {
            AlertDialog.Builder(it)
                .setView(dialogView)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")

        return d
    }
}