package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeRaw> = Updater(context)):
    GenericGetUuidRepository<ReceipeRaw, Receipe>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeDao()
    fun search(name: String): LiveData<List<Receipe>> = getDao().search(NectarUtil.searchMaker(name))

    class Updater(context: Context): GenericEntityUpdater<ReceipeRaw>(context) {
        override fun getUuidType(entity: ReceipeRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.RECEIPE)
    }
}