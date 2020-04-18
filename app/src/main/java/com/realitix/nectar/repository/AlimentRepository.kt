package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.AlimentDao
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class AlimentRepository(val context: Context): GenericRepository<AlimentRaw, Aliment>() {
    override fun getDao() = NectarDatabase.getInstance(context).alimentDao()
    fun search(name: String): LiveData<List<Aliment>> = getDao().search(NectarUtil.searchMaker(name))
}