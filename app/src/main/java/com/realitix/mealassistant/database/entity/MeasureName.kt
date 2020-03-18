package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class MeasureName(measureUuid: String, languageUuid: String, name: String): TagNameRaw(measureUuid, languageUuid, name)

@Entity(
    primaryKeys = ["measureUuid", "languageUuid"],
    foreignKeys = [ForeignKey(
        entity = TagRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["measureUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = LanguageRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["languageUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["measureUuid"]),
        Index(value=["languageUuid"])
    ]
)
open class MeasureNameRaw (
    var measureUuid: String,
    var languageUuid: String,
    var name: String
)