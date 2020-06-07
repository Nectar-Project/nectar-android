package com.realitix.nectar.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.realitix.nectar.database.entity.Measure
import com.realitix.nectar.database.entity.Tag


class TagAddDialogFragment(
    private val listener: Listener
): DialogFragment() {
    private lateinit var d: AlertDialog

    interface Listener {
        fun getOnSelect(index: Int): Tag
        fun onCreate(name: String)
        fun onAdd(tagUuid: String)
        fun getData(): List<String>
    }

    private fun onValidate() {
        EditTextDialogFragment(
            "Tag à créer",
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        d = activity?.let {
            val adapter = ArrayAdapter(it, android.R.layout.simple_selectable_list_item, listener.getData())
            AlertDialog.Builder(it)
                .setTitle("Ajout d'un tag")
                .setAdapter(adapter) { _, which -> listener.onAdd(listener.getOnSelect(which).uuid) }
                .setPositiveButton("Créer un nouveau tag", null)
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