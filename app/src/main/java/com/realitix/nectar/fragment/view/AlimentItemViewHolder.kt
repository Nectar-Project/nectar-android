package com.realitix.nectar.fragment.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.realitix.nectar.R
import com.realitix.nectar.util.SingleLineItemViewHolder

/** A simple two line list item.  */
open class AlimentItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val icon: ImageView = itemView.findViewById(R.id.mtrl_list_item_icon)
    val text: TextView = itemView.findViewById(R.id.mtrl_list_item_text)
    val recyclerView: RecyclerView = itemView.findViewById(R.id.mtrl_list_item_recyclerview)

    companion object {
        fun create(parent: ViewGroup): AlimentItemViewHolder {
            return AlimentItemViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.material_list_item_aliment, parent, false)
            )
        }
    }
}