package com.realitix.nectar.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListAdapter
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class EntityCrudDialogFragment(
    private val title: String,
    private val validateTitle: String,
    private val createHint: String,
    private val listener: OnSelectListener
): DialogFragment() {
    private lateinit var d: AlertDialog
    interface OnSelectListener {
        fun onSelect(index: Int)
        fun onCreate(name: String)
        fun getData(): List<String>
    }

    private fun onValidate() {
        EditTextDialogFragment(
            createHint,
            object :
                EditTextDialogFragment.OnValidateListener {
                override fun onValidate(dialog: EditTextDialogFragment) {
                    // reload
                    val adapter: ArrayAdapter<String> = d.listView.adapter as ArrayAdapter<String>
                    listener.onCreate(dialog.getText())
                    adapter.clear()
                    adapter.addAll(listener.getData())
                    adapter.notifyDataSetChanged()
                }
            }
        ).show(parentFragmentManager, "insertState")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        d = activity?.let {
            val adapter = ArrayAdapter(it, android.R.layout.simple_selectable_list_item, listener.getData())
            AlertDialog.Builder(it)
                .setTitle(title)
                .setAdapter(adapter) { _, which -> listener.onSelect(which)}
                .setPositiveButton(validateTitle, null)
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