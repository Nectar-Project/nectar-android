package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class MealAlimentRepository(val context: Context): GenericCrudRepository<MealAlimentRaw, MealAliment>() {
    override fun getDao() = NectarDatabase.getInstance(context).mealAlimentDao()
}