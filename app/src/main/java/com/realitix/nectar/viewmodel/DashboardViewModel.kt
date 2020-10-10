package com.realitix.nectar.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.NectarUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DashboardViewModel(
    private val rMeal: MealRepository,
    private val rReceipeStep: ReceipeStepRepository,
    private val rShoppingList: ShoppingListRepository,
    private val rShoppingListAlimentState: ShoppingListAlimentStateRepository,
    private val timestamp: Long
): ViewModel() {
    val shoppingLists: LiveData<List<ShoppingList>> = rShoppingList.listLive()

    fun computeShoppingList(begin: Long, end: Long) {
        GlobalScope.launch {
            val res = mutableListOf<Pair<AlimentState, Int>>()
            val meals = rMeal.listRangeSuspend(begin, end)
            for(meal in meals) {
                NectarUtil.addAlimentStateListToList(res, meal.listAliments(rReceipeStep))
            }

            val shoppingListUuid = NectarUtil.generateUuid()
            rShoppingList.insertSuspend(ShoppingListRaw(shoppingListUuid, begin, end))

            for(r in res) {
                rShoppingListAlimentState.insertSuspend(ShoppingListAlimentStateRaw(shoppingListUuid, r.first.uuid, r.second, false))
            }
        }
    }

    fun computeSteps(): List<Pair<Long, ReceipeStep>> {
        val meals = runBlocking {
            rMeal.listFromSuspend(timestamp)
        }

        val steps = mutableListOf<Pair<Long, ReceipeStep>>()
        for(meal in meals) {
            for(mreceipe in meal.receipes) {
                steps.addAll(mreceipe.receipe.getStepsWithTimestamp(rReceipeStep, meal.timestamp))
            }
        }

        steps.sortBy { it.first }
        return steps
    }
}