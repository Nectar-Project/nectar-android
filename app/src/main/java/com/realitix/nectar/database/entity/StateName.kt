package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class StateName(stateUuid: String, language: String, name: String): StateNameRaw(stateUuid, language, name)

@Entity(
    primaryKeys = ["stateUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = StateRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["stateUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["stateUuid"]),
        Index(value=["language"])
    ]
)
open class StateNameRaw (
    var stateUuid: String,
    var language: String,
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StateNameRaw

        if (stateUuid != other.stateUuid) return false
        if (language != other.language) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stateUuid.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}