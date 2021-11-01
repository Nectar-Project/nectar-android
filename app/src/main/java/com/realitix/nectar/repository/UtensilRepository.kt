package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class UtensilRepository(val context: Context, updater: EntityUpdaterInterface<UtensilRaw> = Updater(context)):
    NameGenericRepository<UtensilRaw, Utensil>(updater) {
    override fun getNameUuid(uuid: String) = get(uuid)!!.nameUuid
    override fun getDao() = NectarDatabase.getInstance(context).utensilDao()

    class Updater(context: Context): GenericEntityUpdater<UtensilRaw>(context) {
        override fun getUuidType(entity: UtensilRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.UTENSIL)
    }
}