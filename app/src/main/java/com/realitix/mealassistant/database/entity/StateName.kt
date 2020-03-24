package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class StateName(stateUuid: String, language: String, name: String): StateNameRaw(stateUuid, language, name)

@Entity(
    primaryKeys = ["stateUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = StateRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["stateUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["stateUuid"]),
        Index(value=["language"])
    ]
)
open class StateNameRaw (
    var stateUuid: String,
    var language: String,
    var name: String
)