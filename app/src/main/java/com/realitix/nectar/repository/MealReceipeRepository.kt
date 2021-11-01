package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType

class MealReceipeRepository(val context: Context, updater: EntityUpdaterInterface<MealReceipeRaw> = Updater(context)):
    GenericGetJoinRepository<MealReceipeRaw, MealReceipe>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).mealReceipeDao()

    class Updater(context: Context): GenericEntityUpdater<MealReceipeRaw>(context) {
        override fun getUuidType(entity: MealReceipeRaw) = Pair(entity.mealUuid, EntityType.MEAL)
    }
}