package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.MeasureRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class MeasureSynchronizer(val rMeasure: MeasureRepository, baseRepositoryFolder: File):
    NameBaseSynchronizer<MeasureRaw, Measure>(rMeasure, baseRepositoryFolder) {
    override fun getNew(uuid: String, nameUuid: String) = MeasureRaw(uuid, nameUuid)
    override fun getEntityType(): EntityType = EntityType.MEASURE

    override fun fromGitDeleteInDb(uuid: String) {
       rMeasure.delete(rMeasure.get(uuid)!!)
    }
}