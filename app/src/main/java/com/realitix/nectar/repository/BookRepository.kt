package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.*


class BookRepository(val context: Context): GenericRepository<BookRaw, Book>() {
    override fun getDao() = NectarDatabase.getInstance(context).bookDao()
}