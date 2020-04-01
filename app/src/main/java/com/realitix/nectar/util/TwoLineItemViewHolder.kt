package com.realitix.nectar.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.realitix.nectar.R

/** A simple two line list item.  */
open class TwoLineItemViewHolder(view: View) :
    SingleLineItemViewHolder(view) {
    val secondary: TextView = itemView.findViewById(R.id.mtrl_list_item_secondary_text)

    companion object {
        fun create(parent: ViewGroup): TwoLineItemViewHolder {
            return TwoLineItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.material_list_item_two_line, parent, false)
            )
        }
    }
}