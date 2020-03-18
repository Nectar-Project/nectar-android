package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class AlimentName(alimentUuid: String, languageUuid: String, name: String): AlimentNameRaw(alimentUuid, languageUuid, name)

@Entity(
    primaryKeys = ["alimentUuid", "languageUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = LanguageRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["languageUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"]),
        Index(value=["languageUuid"])
    ]
)
open class AlimentNameRaw (
    var alimentUuid: String,
    var languageUuid: String,
    var name: String
)