package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.DatabaseUpdate
import com.realitix.nectar.database.entity.DatabaseUpdateRaw

class DatabaseUpdateRepository(val context: Context): GenericCrudRepository<DatabaseUpdateRaw, DatabaseUpdate>() {
    override fun getDao() = NectarDatabase.getInstance(context).databaseUpdateDao()
}