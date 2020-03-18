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
)