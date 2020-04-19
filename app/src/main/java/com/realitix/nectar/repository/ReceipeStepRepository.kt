package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class ReceipeStepRepository(val context: Context): GenericCrudRepository<ReceipeStepRaw, ReceipeStep>() {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepDao()
}