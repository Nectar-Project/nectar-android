package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ShoppingListAlimentStateRepository(val context: Context):
    GenericGetJoinRepository<ShoppingListAlimentStateRaw, ShoppingListAlimentState>(NoTrackEntityUpdater()) {
    override fun getDao() = NectarDatabase.getInstance(context).shoppingListAlimentStateDao()
}