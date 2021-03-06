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

class ShoppingListViewModel(
    private val rShoppingList: ShoppingListRepository,
    private val rShoppingListAlimentState: ShoppingListAlimentStateRepository,
    private val shoppingListUuid: String
): ViewModel() {
    val shoppingList: LiveData<ShoppingList> = rShoppingList.getLive(shoppingListUuid)

    fun toggleShoppingListAlimentState(a: ShoppingListAlimentState) {
        GlobalScope.launch {
            a.checked = a.checked.not()
            rShoppingListAlimentState.updateSuspend(a)
        }
    }

    fun deleteShoppingList() {
        GlobalScope.launch {
            rShoppingList.deleteSuspend(rShoppingList.getSuspend(shoppingListUuid)!!)
        }
    }
}