package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class StringKeyValue(stringKeyUuid: String, language: String, value: String): StringKeyValueRaw(stringKeyUuid, language, value)

@Entity(
    primaryKeys = ["stringKeyUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = StringKeyRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["stringKeyUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["stringKeyUuid"]),
        Index(value=["language"])
    ]
)
open class StringKeyValueRaw (
    var stringKeyUuid: String,
    var language: String,
    var value: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringKeyValueRaw

        if (stringKeyUuid != other.stringKeyUuid) return false
        if (language != other.language) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stringKeyUuid.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

}