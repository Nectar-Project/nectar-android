package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class TagRepository(val context: Context): NameRepositoryInterface<TagRaw> {
    override fun insert(i: TagRaw) {
        return NectarDatabase.getInstance(context).tagDao().insert(i)
    }

    override fun getRaw(uuid: String): TagRaw? {
        return NectarDatabase.getInstance(context).tagDao().get(uuid)
    }

    override fun getNameUuid(uuid: String): String {
        return getRaw(uuid)!!.nameUuid
    }
}