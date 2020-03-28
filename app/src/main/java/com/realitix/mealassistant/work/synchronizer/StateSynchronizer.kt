package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.NameRepositoryInterface
import com.realitix.mealassistant.repository.StateRepository
import com.realitix.mealassistant.util.EntityType

class StateSynchronizer: NameBaseSynchronizer<StateRaw, StateNameRaw>() {
    override fun getRepository(context: Context): NameRepositoryInterface<StateRaw, StateNameRaw> = StateRepository.getInstance(context)
    override fun getNew(uuid: String): StateRaw = StateRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): StateNameRaw = StateNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.STATE
}