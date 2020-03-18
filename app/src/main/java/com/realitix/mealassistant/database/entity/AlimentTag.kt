package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class AlimentTag(alimentUuid: String, tagUuid: String): AlimentTagRaw(alimentUuid, tagUuid)

@Entity(
    primaryKeys = ["alimentUuid", "tagUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = TagRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["tagUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"]),
        Index(value=["tagUuid"])
    ]
)
open class AlimentTagRaw (
    var alimentUuid: String,
    var tagUuid: String
)