package com.realitix.mealassistant.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.realitix.mealassistant.R

/** A simple three line list item.  */
class ThreeLineItemViewHolder(view: View) : TwoLineItemViewHolder(view) {
    val tertiary: TextView = itemView.findViewById(R.id.mtrl_list_item_tertiary_text)

    companion object {
        fun create(parent: ViewGroup): ThreeLineItemViewHolder {
            return ThreeLineItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.material_list_item_three_line, parent, false)
            )
        }
    }
}