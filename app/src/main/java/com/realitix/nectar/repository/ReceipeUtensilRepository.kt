package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class ReceipeUtensilRepository(val context: Context, updater: EntityUpdaterInterface<ReceipeUtensilRaw> = Updater(context)):
    GenericGetJoinRepository<ReceipeUtensilRaw, ReceipeUtensil>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).receipeUtensilDao()

    class Updater(context: Context): GenericEntityUpdater<ReceipeUtensilRaw>(context) {
        override fun newDatabaseUpdate(entity: ReceipeUtensilRaw) = DatabaseUpdateRaw(
            entity.receipeUuid, EntityType.RECEIPE, NectarUtil.timestamp())
    }
}