package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Measure(uuid: String): MeasureRaw(uuid) {
    @Relation(parentColumn = "uuid", entityColumn = "measureUuid", entity = MeasureNameRaw::class)
    lateinit var names: List<MeasureName>
}

@Entity
open class MeasureRaw (
    @PrimaryKey
    var uuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MeasureRaw

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}