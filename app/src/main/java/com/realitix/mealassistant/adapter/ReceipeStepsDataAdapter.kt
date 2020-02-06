package com.realitix.mealassistant.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.databinding.FragmentReceipeRecyclerviewStepBinding
import com.realitix.mealassistant.databinding.ReceipeInListBinding


class ReceipeStepsDataAdapter: RecyclerView.Adapter<ReceipeStepsDataAdapter.ReceipeViewHolder>() {
    private var dataset: List<ReceipeStepDao.ReceipeStepFull>? = null

    class ReceipeViewHolder(val binding: FragmentReceipeRecyclerviewStepBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceipeViewHolder {
        val binding: FragmentReceipeRecyclerviewStepBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fragment_receipe_recyclerview_step, parent, false
        )

        return ReceipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if( dataset == null)
            return 0
        return dataset!!.size
    }

    override fun onBindViewHolder(holder: ReceipeViewHolder, position: Int) {
        val currentStep = dataset!![position]
        holder.binding.step = currentStep
    }

    fun setSteps(steps: List<ReceipeStepDao.ReceipeStepFull>?) {
        dataset = steps
        notifyDataSetChanged()
    }

    /*fun getReceipeAtPosition(position: Int): Receipe {
        return dataset!![position]
    }*/
}