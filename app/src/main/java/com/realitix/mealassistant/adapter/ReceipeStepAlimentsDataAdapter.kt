package com.realitix.mealassistant.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.realitix.mealassistant.R
import com.realitix.mealassistant.database.dao.ReceipeStepAlimentDao
import com.realitix.mealassistant.database.dao.ReceipeStepDao
import com.realitix.mealassistant.database.entity.Receipe
import com.realitix.mealassistant.database.entity.ReceipeStep
import com.realitix.mealassistant.database.entity.ReceipeStepAliment
import com.realitix.mealassistant.databinding.FragmentReceipeRecyclerviewStepBinding
import com.realitix.mealassistant.databinding.FragmentReceipeStepRecyclerviewAlimentBinding
import com.realitix.mealassistant.databinding.ReceipeInListBinding


class ReceipeStepAlimentsDataAdapter: RecyclerView.Adapter<ReceipeStepAlimentsDataAdapter.ReceipeViewHolder>() {
    private var dataset: List<ReceipeStepAlimentDao.ReceipeStepAlimentFull>? = null

    class ReceipeViewHolder(val binding: FragmentReceipeStepRecyclerviewAlimentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceipeViewHolder {
        val binding: FragmentReceipeStepRecyclerviewAlimentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fragment_receipe_step_recyclerview_aliment, parent, false
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
        holder.binding.stepAliment = currentStep
    }

    fun setSteps(steps: List<ReceipeStepAlimentDao.ReceipeStepAlimentFull>?) {
        dataset = steps
        notifyDataSetChanged()
    }
}