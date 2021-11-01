package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class MealAlimentRepository(val context: Context, updater: EntityUpdaterInterface<MealAlimentStateRaw> = Updater(context)):
    GenericGetJoinRepository<MealAlimentStateRaw, MealAlimentState>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).mealAlimentDao()

    class Updater(context: Context): GenericEntityUpdater<MealAlimentStateRaw>(context) {
        override fun getUuidType(entity: MealAlimentStateRaw): Pair<String, EntityType> = Pair(entity.mealUuid, EntityType.MEAL)
    }
}