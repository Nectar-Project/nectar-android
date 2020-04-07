package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.StateRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class StateSynchronizer(repository: StateRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<StateRaw, StateNameRaw>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String): StateRaw = StateRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): StateNameRaw = StateNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.STATE
}