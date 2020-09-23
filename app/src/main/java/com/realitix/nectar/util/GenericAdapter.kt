package com.realitix.nectar.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class GenericAdapter<T: RecyclerView.ViewHolder, U>(
    val onCreate: (ViewGroup) -> T,
    val onBind: (T, U) -> Unit
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

    fun addData(d: U) {
        val newData = data?.toMutableList() ?: mutableListOf()
        newData.add(d)
        data = newData
        notifyDataSetChanged()
    }

    fun getAtPosition(position: Int): U {
        return data!![position]
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val currentData = data!![position]
        onBind(holder, currentData)
    }


}