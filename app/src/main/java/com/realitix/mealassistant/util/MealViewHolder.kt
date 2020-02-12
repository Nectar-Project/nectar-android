package com.realitix.mealassistant.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R

class MealViewHolder {
    class ReceipeListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var text: TextView = itemView.findViewById(R.id.receipe_in_list_txt)

        companion object {
            fun create(parent: ViewGroup): ReceipeListViewHolder {
                return ReceipeListViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.receipe_in_list, parent, false)
                )
            }
        }
    }
}