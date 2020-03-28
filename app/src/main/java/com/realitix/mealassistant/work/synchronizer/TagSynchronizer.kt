package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.NameRepositoryInterface
import com.realitix.mealassistant.repository.TagRepository
import com.realitix.mealassistant.util.EntityType

class TagSynchronizer(context: Context, repository: TagRepository):
    NameBaseSynchronizer<TagRaw, TagNameRaw>(context, repository) {
    override fun getNew(uuid: String): TagRaw = TagRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): TagNameRaw = TagNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.TAG
}