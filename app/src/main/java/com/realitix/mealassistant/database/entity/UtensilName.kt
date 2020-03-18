package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


class UtensilName(utensilUuid: String, language: String, name: String): UtensilNameRaw(utensilUuid, language, name)

@Entity(
    primaryKeys = ["utensilUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = UtensilRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["utensilUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["utensilUuid"]),
        Index(value=["language"])
    ]
)
open class UtensilNameRaw (
    var utensilUuid: String,
    var language: String,
    var name: String
)