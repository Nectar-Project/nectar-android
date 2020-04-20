package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class BookReceipeRepository(val context: Context, updater: EntityUpdaterInterface<BookReceipeRaw> = Updater(context)):
    GenericGetJoinRepository<BookReceipeRaw, BookReceipe>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).bookReceipeDao()

    class Updater(context: Context): GenericEntityUpdater<BookReceipeRaw>(context) {
        override fun newDatabaseUpdate(entity: BookReceipeRaw) = DatabaseUpdateRaw(
            entity.bookUuid, EntityType.BOOK, NectarUtil.timestamp())
    }
}