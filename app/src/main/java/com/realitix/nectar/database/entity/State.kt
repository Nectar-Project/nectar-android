package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class State(uuid: String): StateRaw(uuid) {
    @Relation(parentColumn = "uuid", entityColumn = "stateUuid", entity = StateNameRaw::class)
    lateinit var names: List<StateName>
}

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