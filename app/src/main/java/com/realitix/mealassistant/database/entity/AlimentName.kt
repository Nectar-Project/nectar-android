package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class AlimentName(alimentUuid: String, language: String, name: String): AlimentNameRaw(alimentUuid, language, name)

@Entity(
    primaryKeys = ["alimentUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"]),
        Index(value=["language"])
    ]
)
open class AlimentNameRaw (
    var alimentUuid: String,
    var language: String,
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentNameRaw

        if (alimentUuid != other.alimentUuid) return false
        if (language != other.language) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentUuid.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}