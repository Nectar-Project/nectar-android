package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class MealRepository(val context: Context, updater: EntityUpdaterInterface<MealRaw> = Updater(context)):
    GenericGetUuidRepository<MealRaw, Meal>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).mealDao()

    fun listDay(timestamp: Long): LiveData<List<Meal>> = getDao().search(
        NectarUtil.beginDayTimestamp(timestamp),
        NectarUtil.endDayTimestamp(timestamp))

    fun listRange(begin: Long, end: Long): LiveData<List<Meal>> = getDao().search(begin, end)

    class Updater(context: Context): GenericEntityUpdater<MealRaw>(context) {
        override fun newDatabaseUpdate(entity: MealRaw) = DatabaseUpdateRaw(
            entity.uuid, EntityType.MEAL, NectarUtil.timestamp())
    }
}