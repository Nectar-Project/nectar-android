package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentPriceRepository(val context: Context, updater: EntityUpdaterInterface<AlimentPriceRaw> = Updater(context)):
    GenericGetUuidRepository<AlimentPriceRaw, AlimentPrice>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentPriceDao()

    class Updater(context: Context): GenericEntityUpdater<AlimentPriceRaw>(context) {
        override fun getUuidType(entity: AlimentPriceRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.ALIMENT_PRICE)
    }
}