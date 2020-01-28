package com.realitix.mealassistant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.databinding.ReceipeInListBinding


class ReceipeDataAdapter: RecyclerView.Adapter<ReceipeDataAdapter.ReceipeViewHolder>() {
    private var dataset: List<Receipe>? = null

    class ReceipeViewHolder(val binding: ReceipeInListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceipeViewHolder {
        val binding: ReceipeInListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.receipe_in_list, parent, false
        )

        return ReceipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if( dataset == null)
            return 0
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ReceipeViewHolder, position: Int) {
        val currentReceipe: String = dataset!![position].name
        holder.binding.receipe = currentReceipe
    }

    fun setReceipes(receipes: List<Receipe>) {
        dataset = receipes
        notifyDataSetChanged()
    }

    fun getReceipeAtPosition(position: Int): Receipe {
        return dataset!![position]
    }
}