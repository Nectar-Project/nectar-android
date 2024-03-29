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

    fun listDayLive(timestamp: Long): LiveData<List<Meal>> = getDao().searchLive(
        NectarUtil.beginDayTimestamp(timestamp),
        NectarUtil.endDayTimestamp(timestamp))

    suspend fun listRangeSuspend(begin: Long, end: Long): List<Meal> = getDao().searchSuspend(begin, end)
    suspend fun listFromSuspend(from: Long): List<Meal> = getDao().searchFromSuspend(from)

    class Updater(context: Context): GenericEntityUpdater<MealRaw>(context) {
        override fun getUuidType(entity: MealRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.MEAL)
    }
}