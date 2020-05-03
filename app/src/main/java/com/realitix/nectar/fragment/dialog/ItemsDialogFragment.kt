package com.realitix.nectar.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class ItemsDialogFragment(
    private val title: String,
    private val values: List<String>,
    private val listener: OnSelectListener,
    private val validateTitle: String = "Valider"
): DialogFragment() {
    interface OnSelectListener {
        fun onSelect(index: Int)
        fun onValidate()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d = activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setItems(values.toTypedArray()) { _, which -> listener.onSelect(which) }
                .setPositiveButton(validateTitle, null)
                .setNegativeButton("Annuler", null)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
        d.setOnShowListener {
            d.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                listener.onValidate()
                
            }

        }
        return d
    }
}