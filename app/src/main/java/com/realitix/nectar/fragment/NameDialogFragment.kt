package com.realitix.nectar.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class NameDialogFragment(private val listener: OnValidateListener) : DialogFragment() {
    private lateinit var editText: EditText

    interface OnValidateListener {
        fun onValidate(dialog: NameDialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        editText = EditText(requireContext())
        editText.hint = "Nom à donner"
        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Nom")
                .setView(editText)
                .setPositiveButton("Valider") { _, _ -> listener.onValidate(this) }
                .setNegativeButton("Annuler") { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun getName(): String {
        return editText.text.toString()
    }
}