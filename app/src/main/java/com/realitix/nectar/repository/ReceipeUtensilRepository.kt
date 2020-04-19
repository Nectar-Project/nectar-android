package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class ReceipeUtensilRepository(val context: Context): GenericCrudRepository<ReceipeUtensilRaw, ReceipeUtensil>() {
    override fun getDao() = NectarDatabase.getInstance(context).receipeUtensilDao()
}