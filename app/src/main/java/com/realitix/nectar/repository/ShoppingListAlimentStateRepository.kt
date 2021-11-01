package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ShoppingListAlimentStateRepository(val context: Context,  updater: EntityUpdaterInterface<ShoppingListAlimentStateRaw> = Updater(context)):
    GenericGetJoinRepository<ShoppingListAlimentStateRaw, ShoppingListAlimentState>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).shoppingListAlimentStateDao()

    class Updater(context: Context): GenericEntityUpdater<ShoppingListAlimentStateRaw>(context) {
        override fun getUuidType(entity: ShoppingListAlimentStateRaw): Pair<String, EntityType> = Pair(entity.shoppingListUuid, EntityType.SHOPPING_LIST)
    }
}