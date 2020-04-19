package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class MealReceipeRepository(val context: Context): GenericCrudRepository<MealReceipeRaw, MealReceipe>() {
    override fun getDao() = NectarDatabase.getInstance(context).mealReceipeDao()
}