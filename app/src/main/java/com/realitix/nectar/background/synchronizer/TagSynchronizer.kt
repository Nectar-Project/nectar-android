package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.TagRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class TagSynchronizer(repository: TagRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<TagRaw, TagNameRaw>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String): TagRaw = TagRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): TagNameRaw = TagNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.TAG
}