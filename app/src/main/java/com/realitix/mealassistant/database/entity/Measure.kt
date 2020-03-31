package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


class Measure(uuid: String): MeasureRaw(uuid)

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