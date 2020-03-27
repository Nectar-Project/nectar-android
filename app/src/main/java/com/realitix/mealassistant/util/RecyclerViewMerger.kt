package com.realitix.mealassistant.util

import com.realitix.mealassistant.database.entity.MealAliment
import com.realitix.mealassistant.database.entity.MealReceipe
import com.realitix.mealassistant.database.entity.ReceipeStepAliment
import com.realitix.mealassistant.database.entity.ReceipeStepReceipe

class RecyclerViewMerger (var text: String, var secondary: String) {
    companion object {
        @JvmStatic
        @JvmName("from1")
        fun from(
            aliments: List<ReceipeStepAliment>,
            receipes: List<ReceipeStepReceipe>
        ): ArrayList<RecyclerViewMerger> {
            val mergedList = ArrayList<RecyclerViewMerger>(aliments.size + receipes.size)
            for (a in aliments) {
                mergedList.add(RecyclerViewMerger(a.aliment.getName(), a.quantity.toString()+"g"))
            }
            for (r in receipes) {
                mergedList.add(RecyclerViewMerger(r.receipe.getName(), "test"))
            }

            return mergedList
        }

        @JvmStatic
        @JvmName("from2")
        fun from(
            aliments: List<MealAliment>,
            receipes: List<MealReceipe>
        ): ArrayList<RecyclerViewMerger> {
            val mergedList = ArrayList<RecyclerViewMerger>(aliments.size + receipes.size)
            for (a in aliments) {
                mergedList.add(RecyclerViewMerger(a.aliment.getName(), a.quantity.toString()+"g"))
            }
            for (r in receipes) {
                mergedList.add(RecyclerViewMerger(r.receipe.getName(), "test"))
            }

            return mergedList
        }
    }
}