package com.realitix.mealassistant.repository

import android.content.Context
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.*


class TagRepository(val context: Context): NameRepositoryInterface<TagRaw, TagNameRaw> {
    override fun insert(i: TagRaw) {
        return MealDatabase.getInstance(context).tagDao().insert(i)
    }

    override fun insertName(i: TagNameRaw) {
        return MealDatabase.getInstance(context).tagNameDao().insert(i)
    }

    override fun getRaw(uuid: String): TagRaw? {
        return MealDatabase.getInstance(context).tagDao().get(uuid)
    }
}