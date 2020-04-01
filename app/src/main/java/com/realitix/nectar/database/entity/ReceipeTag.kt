package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class ReceipeTag(receipeUuid: String, tagUuid: String): ReceipeTagRaw(receipeUuid, tagUuid)

@Entity(
    primaryKeys = ["receipeUuid", "tagUuid"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = TagRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["tagUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["tagUuid"])
    ]
)
open class ReceipeTagRaw (
    var receipeUuid: String,
    var tagUuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeTagRaw

        if (receipeUuid != other.receipeUuid) return false
        if (tagUuid != other.tagUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = receipeUuid.hashCode()
        result = 31 * result + tagUuid.hashCode()
        return result
    }
}