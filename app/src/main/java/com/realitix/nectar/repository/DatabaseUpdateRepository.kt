package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.DatabaseUpdate
import com.realitix.nectar.database.entity.GitRepository

class DatabaseUpdateRepository(val context: Context) {
    fun list(): List<DatabaseUpdate> = NectarDatabase.getInstance(context).databaseUpdateDao().list()
}