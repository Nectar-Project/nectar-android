package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


class TagName(tagUuid: String, languageUuid: String, name: String): TagNameRaw(tagUuid, languageUuid, name)

@Entity(
    primaryKeys = ["tagUuid", "languageUuid"],
    foreignKeys = [ForeignKey(
        entity = TagRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["tagUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = LanguageRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["languageUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["tagUuid"]),
        Index(value=["languageUuid"])
    ]
)
open class TagNameRaw (
    var tagUuid: String,
    var languageUuid: String,
    var name: String
)