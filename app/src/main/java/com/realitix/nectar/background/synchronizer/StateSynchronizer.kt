package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.StateRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class StateSynchronizer(repository: StateRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<StateRaw, State>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String, nameUuid: String): StateRaw = StateRaw(uuid, nameUuid)
    override fun getEntityType(): EntityType = EntityType.STATE
}