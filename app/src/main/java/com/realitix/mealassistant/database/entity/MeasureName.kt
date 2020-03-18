package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class MeasureName(measureUuid: String, language: String, name: String): TagNameRaw(measureUuid, language, name)

@Entity(
    primaryKeys = ["measureUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = TagRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["measureUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["measureUuid"]),
        Index(value=["language"])
    ]
)
open class MeasureNameRaw (
    var measureUuid: String,
    var language: String,
    var name: String
)