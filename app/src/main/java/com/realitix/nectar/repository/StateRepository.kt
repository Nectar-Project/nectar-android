package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.DatabaseUpdateRaw
import com.realitix.nectar.database.entity.State
import com.realitix.nectar.database.entity.StateRaw
import com.realitix.nectar.database.entity.TagRaw
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class StateRepository(val context: Context, updater: EntityUpdaterInterface<StateRaw> = Updater(context)):
    NameGenericRepository<StateRaw, State>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).stateDao()
    override fun getNameUuid(uuid: String) = get(uuid)!!.nameUuid

    class Updater(context: Context): GenericEntityUpdater<StateRaw>(context) {
        override fun getUuidType(entity: StateRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.STATE)
    }
}