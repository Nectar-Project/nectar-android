package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.MeasureRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class MeasureSynchronizer(repository: MeasureRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<MeasureRaw, MeasureNameRaw>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String): MeasureRaw = MeasureRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): MeasureNameRaw = MeasureNameRaw(uuid, lang, name)
    override fun getEntityType(): EntityType = EntityType.MEASURE
}