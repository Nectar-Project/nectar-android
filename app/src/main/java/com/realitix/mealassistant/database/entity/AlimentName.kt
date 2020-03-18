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
)