package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.UtensilRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class UtensilSynchronizer(repository: UtensilRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<UtensilRaw, UtensilNameRaw>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String): UtensilRaw = UtensilRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): UtensilNameRaw = UtensilNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.UTENSIL
}