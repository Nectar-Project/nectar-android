package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.MealDatabase
import com.realitix.nectar.database.entity.StateNameRaw
import com.realitix.nectar.database.entity.StateRaw


class StateRepository(val context: Context): NameRepositoryInterface<StateRaw, StateNameRaw> {
    override fun insert(i: StateRaw) {
        return MealDatabase.getInstance(context).stateDao().insert(i)
    }

    override fun insertName(i: StateNameRaw) {
        return MealDatabase.getInstance(context).stateNameDao().insert(i)
    }

    override fun getRaw(uuid: String): StateRaw? {
        return MealDatabase.getInstance(context).stateDao().get(uuid)
    }
}