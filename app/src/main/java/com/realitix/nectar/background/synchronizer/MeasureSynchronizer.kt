package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.MeasureRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class MeasureSynchronizer(repository: MeasureRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<MeasureRaw>(repository, baseRepositoryFolder) {
    override fun getNew(uuid: String, nameUuid: String): MeasureRaw = MeasureRaw(uuid, nameUuid)
    override fun getEntityType(): EntityType = EntityType.MEASURE
}