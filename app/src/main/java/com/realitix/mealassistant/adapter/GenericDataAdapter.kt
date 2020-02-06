package com.realitix.mealassistant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.databinding.ReceipeInListBinding
import com.realitix.mealassistant.util.SingleLineItemViewHolder
import com.realitix.mealassistant.util.ThreeLineItemViewHolder
import com.realitix.mealassistant.util.TwoLineItemViewHolder

private const val RECYCLER_ONE = 1
private const val RECYCLER_TWO = 2
private const val RECYCLER_THREE = 3

class GenericDataAdapter<T: RecyclerView.ViewHolder, U>(
    val onCreate: (ViewGroup) -> T,
    val onBind: (T, U) -> Void
): RecyclerView.Adapter<T>() {
    private var data: List<U>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return onCreate(parent)
    }

    override fun getItemCount(): Int {
        if( data == null)
            return 0
        return data!!.size
    }

    fun setData(d: List<U>) {
        data = d
        notifyDataSetChanged()
    }

    fun getDataAtPosition(position: Int): U {
        return data!![position]
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val currentData = data!![position]
        onBind(holder, currentData)
    }


}