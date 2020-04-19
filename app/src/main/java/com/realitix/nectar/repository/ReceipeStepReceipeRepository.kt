package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class ReceipeStepReceipeRepository(val context: Context): GenericCrudRepository<ReceipeStepReceipeRaw, ReceipeStepReceipe>() {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepReceipeDao()
}