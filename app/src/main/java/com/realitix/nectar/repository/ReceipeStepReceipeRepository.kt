package com.realitix.nectar.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.dao.ReceipeDao
import com.realitix.nectar.database.dao.ReceipeTagDao
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class ReceipeStepReceipeRepository(val context: Context): GenericRepository<ReceipeStepReceipeRaw, ReceipeStepReceipe>() {
    override fun getDao() = NectarDatabase.getInstance(context).receipeStepReceipeDao()
}