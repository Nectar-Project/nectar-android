package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class ReceipeName(receipeUuid: String, language: String, name: String): ReceipeNameRaw(receipeUuid, language, name)

@Entity(
    primaryKeys = ["receipeUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["language"])
    ]
)
open class ReceipeNameRaw (
    var receipeUuid: String,
    var language: String,
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeNameRaw

        if (receipeUuid != other.receipeUuid) return false
        if (language != other.language) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = receipeUuid.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}