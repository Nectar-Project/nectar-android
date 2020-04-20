package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class BookRepository(val context: Context, updater: EntityUpdaterInterface<BookRaw> = Updater(context)):
    GenericGetUuidRepository<BookRaw, Book>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).bookDao()

    class Updater(context: Context): GenericEntityUpdater<BookRaw>(context) {
        override fun newDatabaseUpdate(entity: BookRaw) = DatabaseUpdateRaw(
            entity.uuid, EntityType.BOOK, NectarUtil.timestamp())
    }
}