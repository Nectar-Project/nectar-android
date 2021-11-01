package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ShoppingListRepository(val context: Context, updater: EntityUpdaterInterface<ShoppingListRaw> = Updater(context)):
    GenericGetUuidRepository<ShoppingListRaw, ShoppingList>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).shoppingListDao()

    class Updater(context: Context): GenericEntityUpdater<ShoppingListRaw>(context) {
        override fun getUuidType(entity: ShoppingListRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.SHOPPING_LIST)
    }
}