package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.StateNameRaw
import com.realitix.nectar.database.entity.StateRaw


class StateRepository(val context: Context): NameRepositoryInterface<StateRaw, StateNameRaw> {
    override fun insert(i: StateRaw) {
        return NectarDatabase.getInstance(context).stateDao().insert(i)
    }

    override fun insertName(i: StateNameRaw) {
        return NectarDatabase.getInstance(context).stateNameDao().insert(i)
    }

    override fun getRaw(uuid: String): StateRaw? {
        return NectarDatabase.getInstance(context).stateDao().get(uuid)
    }

    override fun getNamesMap(uuid: String): Map<String, String> {
        val state = NectarDatabase.getInstance(context).stateDao().get(uuid)!!
        val result = mutableMapOf<String, String>()
        for(n in state.names) {
            result[n.language] = n.name
        }
        return result
    }
}