package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class MealReceipeRepository(val context: Context): GenericRepository<MealReceipeRaw, MealReceipe>() {
    override fun getDao() = NectarDatabase.getInstance(context).mealReceipeDao()
}