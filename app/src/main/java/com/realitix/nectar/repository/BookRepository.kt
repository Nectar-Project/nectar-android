package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class BookRepository(val context: Context) {
    fun insertBook(i: BookRaw) {
        return NectarDatabase.getInstance(context).bookDao().insert(i)
    }

    fun insertBookName(i: BookNameRaw) {
        return NectarDatabase.getInstance(context).bookNameDao().insert(i)
    }

    fun insertBookImage(i: BookImageRaw) {
        return NectarDatabase.getInstance(context).bookImageDao().insert(i)
    }

    fun insertBookReceipe(i: BookReceipeRaw) {
        return NectarDatabase.getInstance(context).bookReceipeDao().insert(i)
    }

    fun getBook(uuid: String): Book? {
        return NectarDatabase.getInstance(context).bookDao().get(uuid)
    }
}