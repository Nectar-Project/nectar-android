package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentRepository(val context: Context, updater: EntityUpdaterInterface<AlimentRaw> = Updater(context)):
    GenericGetUuidRepository<AlimentRaw, Aliment>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentDao()
    fun search(name: String): LiveData<List<Aliment>> = getDao().search(NectarUtil.searchMaker(name))

    class Updater(context: Context): GenericEntityUpdater<AlimentRaw>(context) {
        override fun getUuidType(entity: AlimentRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.ALIMENT)
    }
}