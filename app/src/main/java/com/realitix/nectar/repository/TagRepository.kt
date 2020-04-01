package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*


class TagRepository(val context: Context): NameRepositoryInterface<TagRaw, TagNameRaw> {
    override fun insert(i: TagRaw) {
        return NectarDatabase.getInstance(context).tagDao().insert(i)
    }

    override fun insertName(i: TagNameRaw) {
        return NectarDatabase.getInstance(context).tagNameDao().insert(i)
    }

    override fun getRaw(uuid: String): TagRaw? {
        return NectarDatabase.getInstance(context).tagDao().get(uuid)
    }

    override fun getNamesMap(uuid: String): Map<String, String> {
        val tag = NectarDatabase.getInstance(context).tagDao().get(uuid)!!
        val result = mutableMapOf<String, String>()
        for(n in tag.names) {
            result[n.language] = n.name
        }
        return result
    }
}