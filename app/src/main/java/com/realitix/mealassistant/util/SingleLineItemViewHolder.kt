package com.realitix.mealassistant.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R
import com.realitix.mealassistant.adapter.GenericDataAdapter

/** A simple single line list item.  */
open class SingleLineItemViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    val icon: ImageView = itemView.findViewById(R.id.mtrl_list_item_icon)
    val text: TextView = itemView.findViewById(R.id.mtrl_list_item_text)

    companion object: GenericDataAdapter.GenericAdapterFactory<SingleLineItemViewHolder> {
        override fun create(parent: ViewGroup): SingleLineItemViewHolder {
            return SingleLineItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.material_list_item_single_line, parent, false)
            )
        }
    }
}