package com.realitix.mealassistant.database.entity

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
)