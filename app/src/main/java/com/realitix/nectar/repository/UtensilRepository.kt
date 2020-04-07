package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class UtensilRepository(val context: Context): NameRepositoryInterface<UtensilRaw> {
    override fun insert(i: UtensilRaw) {
        return NectarDatabase.getInstance(context).utensilDao().insert(i)
    }

    override fun getRaw(uuid: String): UtensilRaw? {
        return NectarDatabase.getInstance(context).utensilDao().get(uuid)
    }

    override fun getNameUuid(uuid: String): String {
        return getRaw(uuid)!!.nameUuid
    }
}