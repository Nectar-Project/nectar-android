package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


class State(uuid: String): StateRaw(uuid)

@Entity
open class StateRaw (
    @PrimaryKey
    var uuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StateRaw

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}