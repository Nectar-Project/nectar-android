package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*

class ImageRepository(val context: Context) {
    fun insert(i: ImageRaw) {
        return NectarDatabase.getInstance(context).imageDao().insert(i)
    }

    fun get(uuid: String): Image? {
        return NectarDatabase.getInstance(context).imageDao().get(uuid)
    }
}