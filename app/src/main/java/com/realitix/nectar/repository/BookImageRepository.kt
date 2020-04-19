package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class BookImageRepository(val context: Context, updater: EntityUpdaterInterface<BookImageRaw> = Updater(context)):
    GenericCrudRepository<BookImageRaw, BookImage>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).bookImageDao()

    class Updater(val context: Context): EntityUpdaterInterface<BookImageRaw> {
        override fun onEntityUpdate(entity: BookImageRaw) {
            NectarDatabase.getInstance(context).databaseUpdateDao().insert(
                DatabaseUpdateRaw(entity.bookUuid, EntityType.BOOK, NectarUtil.timestamp())
            )
        }
    }
}