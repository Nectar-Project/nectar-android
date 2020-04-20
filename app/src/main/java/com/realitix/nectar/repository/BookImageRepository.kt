package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class BookImageRepository(val context: Context, updater: EntityUpdaterInterface<BookImageRaw> = Updater(context)):
    GenericGetJoinRepository<BookImageRaw, BookImage>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).bookImageDao()

    class Updater(context: Context): GenericEntityUpdater<BookImageRaw>(context) {
        override fun newDatabaseUpdate(entity: BookImageRaw) = DatabaseUpdateRaw(
            entity.bookUuid, EntityType.BOOK, NectarUtil.timestamp())
    }
}