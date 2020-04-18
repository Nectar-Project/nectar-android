package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class UtensilRepository(val context: Context): NameGenericRepository<UtensilRaw, Utensil>() {
    override fun getNameUuid(uuid: String) = get(uuid)!!.nameUuid
    override fun getDao() = NectarDatabase.getInstance(context).utensilDao()
}