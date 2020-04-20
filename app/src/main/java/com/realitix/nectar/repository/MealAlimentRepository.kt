package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class MealAlimentRepository(val context: Context, updater: EntityUpdaterInterface<MealAlimentRaw> = Updater(context)):
    GenericGetJoinRepository<MealAlimentRaw, MealAliment>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).mealAlimentDao()

    class Updater(context: Context): GenericEntityUpdater<MealAlimentRaw>(context) {
        override fun newDatabaseUpdate(entity: MealAlimentRaw) = DatabaseUpdateRaw(
            entity.mealUuid, EntityType.MEAL, NectarUtil.timestamp())
    }
}