package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


class TagName(tagUuid: String, language: String, name: String): TagNameRaw(tagUuid, language, name)

@Entity(
    primaryKeys = ["tagUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = TagRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["tagUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["tagUuid"]),
        Index(value=["language"])
    ]
)
open class TagNameRaw (
    var tagUuid: String,
    var language: String,
    var name: String
)