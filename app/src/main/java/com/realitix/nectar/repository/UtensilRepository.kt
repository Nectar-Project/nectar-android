package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.MealDatabase
import com.realitix.nectar.database.entity.*


class UtensilRepository(val context: Context): NameRepositoryInterface<UtensilRaw, UtensilNameRaw> {
    override fun insert(i: UtensilRaw) {
        return MealDatabase.getInstance(context).utensilDao().insert(i)
    }

    override fun insertName(i: UtensilNameRaw) {
        return MealDatabase.getInstance(context).utensilNameDao().insert(i)
    }

    override fun getRaw(uuid: String): UtensilRaw? {
        return MealDatabase.getInstance(context).utensilDao().get(uuid)
    }
}