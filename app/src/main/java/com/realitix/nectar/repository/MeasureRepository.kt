package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.MealDatabase
import com.realitix.nectar.database.entity.*


class MeasureRepository(val context: Context): NameRepositoryInterface<MeasureRaw, MeasureNameRaw> {
    override fun insert(i: MeasureRaw) {
        return MealDatabase.getInstance(context).measureDao().insert(i)
    }

    override fun insertName(i: MeasureNameRaw) {
        return MealDatabase.getInstance(context).measureNameDao().insert(i)
    }

    override fun getRaw(uuid: String): MeasureRaw? {
        return MealDatabase.getInstance(context).measureDao().get(uuid)
    }
}