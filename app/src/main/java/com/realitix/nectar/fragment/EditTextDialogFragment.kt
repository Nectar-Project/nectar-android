package com.realitix.nectar.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class EditTextDialogFragment(
    private val hint: String,
    private val listener: OnValidateListener,
    private val currentValue: String? = null
): DialogFragment() {
    private lateinit var editText: EditText

    interface OnValidateListener {
        fun onValidate(dialog: EditTextDialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        editText = EditText(requireContext())
        editText.hint = hint
        if(currentValue != null) {
            editText.setText(currentValue)
        }
        return activity?.let {
            AlertDialog.Builder(it)
                .setView(editText)
                .setPositiveButton("Valider") { _, _ -> listener.onValidate(this) }
                .setNegativeButton("Annuler") { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun getText(): String {
        return editText.text.toString()
    }
}