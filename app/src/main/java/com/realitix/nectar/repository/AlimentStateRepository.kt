package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class AlimentStateRepository(val context: Context): GenericRepository<AlimentStateRaw, AlimentState>() {
    override fun getDao() = NectarDatabase.getInstance(context).alimentStateDao()
}