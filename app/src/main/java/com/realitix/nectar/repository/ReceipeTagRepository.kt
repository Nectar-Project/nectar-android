package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class ReceipeTagRepository(val context: Context): GenericCrudRepository<ReceipeTagRaw, ReceipeTag>() {
    override fun getDao() = NectarDatabase.getInstance(context).receipeTagDao()
}