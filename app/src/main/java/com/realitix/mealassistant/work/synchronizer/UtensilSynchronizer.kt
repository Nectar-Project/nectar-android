package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.NameRepositoryInterface
import com.realitix.mealassistant.repository.UtensilRepository
import com.realitix.mealassistant.util.EntityType
import java.io.File

class UtensilSynchronizer(repository: UtensilRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<UtensilRaw, UtensilNameRaw>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String): UtensilRaw = UtensilRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): UtensilNameRaw = UtensilNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.UTENSIL
}