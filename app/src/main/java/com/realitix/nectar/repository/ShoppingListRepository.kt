package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ShoppingListRepository(val context: Context):
    GenericGetUuidRepository<ShoppingListRaw, ShoppingList>(NoTrackEntityUpdater()) {
    override fun getDao() = NectarDatabase.getInstance(context).shoppingListDao()
}