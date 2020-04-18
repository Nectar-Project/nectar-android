package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.UtensilRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class UtensilSynchronizer(repository: UtensilRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<UtensilRaw, Utensil>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String, nameUuid: String) = UtensilRaw(uuid, nameUuid)
    override fun getEntityType(): EntityType = EntityType.UTENSIL
}