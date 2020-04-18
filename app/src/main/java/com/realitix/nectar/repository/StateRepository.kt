package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.State
import com.realitix.nectar.database.entity.StateRaw
import com.realitix.nectar.database.entity.TagRaw


class StateRepository(val context: Context): NameGenericRepository<StateRaw, State>() {
    override fun getDao() = NectarDatabase.getInstance(context).stateDao()
    override fun getNameUuid(uuid: String) = get(uuid)!!.nameUuid
}