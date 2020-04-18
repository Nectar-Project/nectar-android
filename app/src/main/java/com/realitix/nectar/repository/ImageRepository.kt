package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.*

class ImageRepository(val context: Context): GenericRepository<ImageRaw, Image>() {
    override fun getDao() = NectarDatabase.getInstance(context).imageDao()
}