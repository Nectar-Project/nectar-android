package com.realitix.nectar.util

import com.realitix.nectar.database.entity.MealAlimentState
import com.realitix.nectar.database.entity.MealReceipe
import com.realitix.nectar.database.entity.ReceipeStepAlimentState
import com.realitix.nectar.database.entity.ReceipeStepReceipe

class RecyclerViewMerger (var text: String, var secondary: String) {
    companion object {
        @JvmStatic
        @JvmName("from1")
        fun from(
            aliments: List<ReceipeStepAlimentState>,
            receipes: List<ReceipeStepReceipe>
        ): ArrayList<RecyclerViewMerger> {
            val mergedList = ArrayList<RecyclerViewMerger>(aliments.size + receipes.size)
            for (a in aliments) {
                mergedList.add(RecyclerViewMerger(a.alimentState.aliment.getName(), a.weight.toString()+"g"))
            }
            for (r in receipes) {
                mergedList.add(RecyclerViewMerger(r.receipe.getName(), "proportion: "+r.proportion.toString()))
            }

            return mergedList
        }

        @JvmStatic
        @JvmName("from2")
        fun from(
            aliments: List<MealAlimentState>,
            receipes: List<MealReceipe>
        ): ArrayList<RecyclerViewMerger> {
            val mergedList = ArrayList<RecyclerViewMerger>(aliments.size + receipes.size)
            for (a in aliments) {
                mergedList.add(RecyclerViewMerger(a.alimentState.aliment.getName(), a.weight.toString()+"g"))
            }
            for (r in receipes) {
                mergedList.add(RecyclerViewMerger(r.receipe.getName(), "proportion: "+r.proportion.toString()))
            }

            return mergedList
        }
    }
}