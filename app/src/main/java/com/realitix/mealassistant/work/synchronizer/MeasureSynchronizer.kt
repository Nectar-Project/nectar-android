package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.MeasureRepository
import com.realitix.mealassistant.repository.NameRepositoryInterface
import com.realitix.mealassistant.util.EntityType

class MeasureSynchronizer(context: Context, repository: MeasureRepository):
    NameBaseSynchronizer<MeasureRaw, MeasureNameRaw>(context, repository) {
    override fun getNew(uuid: String): MeasureRaw = MeasureRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): MeasureNameRaw = MeasureNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.MEASURE
}