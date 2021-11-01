package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil


class TagRepository(val context: Context, updater: EntityUpdaterInterface<TagRaw> = Updater(context)):
    NameGenericRepository<TagRaw, Tag>(updater) {
    override fun getNameUuid(uuid: String) = get(uuid)!!.nameUuid
    override fun getDao() = NectarDatabase.getInstance(context).tagDao()

    class Updater(context: Context): GenericEntityUpdater<TagRaw>(context) {
        override fun getUuidType(entity: TagRaw): Pair<String, EntityType> = Pair(entity.uuid, EntityType.TAG)
    }
}