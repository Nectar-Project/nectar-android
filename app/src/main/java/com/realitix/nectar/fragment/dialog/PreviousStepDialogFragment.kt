package com.realitix.nectar.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment


class PreviousStepDialogFragment(
    private val title: String,
    private val listener: Listener
): DialogFragment() {
    private lateinit var d: AlertDialog

    interface Listener {
        fun onSelect(index: Int)
        fun getData(): List<String>
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        d = activity?.let {
            val adapter = ArrayAdapter(it, android.R.layout.simple_selectable_list_item, listener.getData())
            AlertDialog.Builder(it)
                .setTitle(title)
                .setAdapter(adapter) { _, which -> listener.onSelect(which)}
                .setNegativeButton("Annuler", null)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")

        return d
    }
}