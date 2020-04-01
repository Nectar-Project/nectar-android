package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class UtensilRepository(val context: Context): NameRepositoryInterface<UtensilRaw, UtensilNameRaw> {
    override fun insert(i: UtensilRaw) {
        return NectarDatabase.getInstance(context).utensilDao().insert(i)
    }

    override fun insertName(i: UtensilNameRaw) {
        return NectarDatabase.getInstance(context).utensilNameDao().insert(i)
    }

    override fun getRaw(uuid: String): UtensilRaw? {
        return NectarDatabase.getInstance(context).utensilDao().get(uuid)
    }

    override fun getNamesMap(uuid: String): Map<String, String> {
        val utensil = NectarDatabase.getInstance(context).utensilDao().get(uuid)!!
        val result = mutableMapOf<String, String>()
        for(n in utensil.names) {
            result[n.language] = n.name
        }
        return result
    }
}