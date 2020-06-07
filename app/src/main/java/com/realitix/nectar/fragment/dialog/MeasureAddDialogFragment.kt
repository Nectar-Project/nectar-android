package com.realitix.nectar.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.realitix.nectar.database.entity.Measure


class MeasureAddDialogFragment(
    private val listener: Listener
): DialogFragment() {
    private lateinit var d: AlertDialog
    private lateinit var measure: Measure

    interface Listener {
        fun getOnSelect(index: Int): Measure
        fun onCreate(name: String)
        fun onAdd(measureUuid: String, quantity: Int)
        fun getData(): List<String>
    }

    private fun onValidate() {
        EditTextDialogFragment(
            "Mesure à créer",
            object :
                EditTextDialogFragment.OnValidateListener {
                override fun onValidate(dialog: EditTextDialogFragment) {
                    // reload
                    @Suppress("UNCHECKED_CAST")
                    val adapter: ArrayAdapter<String> = d.listView.adapter as ArrayAdapter<String>
                    listener.onCreate(dialog.getText())
                    adapter.clear()
                    adapter.addAll(listener.getData())
                    adapter.notifyDataSetChanged()
                }
            }
        ).show(parentFragmentManager, "insert")
    }

    private fun onSelect(index: Int) {
        measure = listener.getOnSelect(index)
        EditTextDialogFragment(
            "Quantité de la mesure " + measure.getName(),
            object :
                EditTextDialogFragment.OnValidateListener {
                override fun onValidate(dialog: EditTextDialogFragment) {
                    listener.onAdd(measure.uuid, dialog.getText().toInt())
                }
            }
        ).show(parentFragmentManager, "insert")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        d = activity?.let {
            val adapter = ArrayAdapter(it, android.R.layout.simple_selectable_list_item, listener.getData())
            AlertDialog.Builder(it)
                .setTitle("Ajout d'une mesure")
                .setAdapter(adapter) { _, which -> onSelect(which) }
                .setPositiveButton("Créer une nouvelle mesure", null)
                .setNegativeButton("Annuler", null)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
        d.setOnShowListener {
            d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                onValidate()
            }
        }

        return d
    }
}