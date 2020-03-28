package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.NameRepositoryInterface
import com.realitix.mealassistant.repository.UtensilRepository
import com.realitix.mealassistant.util.EntityType

class UtensilSynchronizer(context: Context, repository: UtensilRepository):
    NameBaseSynchronizer<UtensilRaw, UtensilNameRaw>(context, repository) {
    override fun getNew(uuid: String): UtensilRaw = UtensilRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): UtensilNameRaw = UtensilNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.UTENSIL
}