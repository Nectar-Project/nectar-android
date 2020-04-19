package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class ReceipeStepAlimentRepository(val context: Context): GenericCrudRepository<ReceipeStepAlimentRaw, ReceipeStepAliment>() {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepAlimentDao()
}