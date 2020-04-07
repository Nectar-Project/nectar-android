package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.StateRaw
import com.realitix.nectar.database.entity.TagRaw


class StateRepository(val context: Context): NameRepositoryInterface<StateRaw> {
    override fun insert(i: StateRaw) {
        return NectarDatabase.getInstance(context).stateDao().insert(i)
    }

    override fun getRaw(uuid: String): StateRaw? {
        return NectarDatabase.getInstance(context).stateDao().get(uuid)
    }

    override fun getNameUuid(uuid: String): String {
        return getRaw(uuid)!!.nameUuid
    }
}