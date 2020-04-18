package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.TagRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class TagSynchronizer(repository: TagRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<TagRaw, Tag>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String, nameUuid: String) = TagRaw(uuid, nameUuid)
    override fun getEntityType(): EntityType = EntityType.TAG
}