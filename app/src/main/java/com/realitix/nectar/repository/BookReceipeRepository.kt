package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class BookReceipeRepository(val context: Context): GenericCrudRepository<BookReceipeRaw, BookReceipe>() {
    override fun getDao() = NectarDatabase.getInstance(context).bookReceipeDao()
}