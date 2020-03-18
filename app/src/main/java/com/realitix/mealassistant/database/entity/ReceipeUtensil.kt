package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class ReceipeUtensil(receipeUuid: String, utensilUuid: String): ReceipeUtensilRaw(receipeUuid, utensilUuid)

@Entity(
    primaryKeys = ["receipeUuid", "utensilUuid"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UtensilRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["utensilUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["utensilUuid"])
    ]
)
open class ReceipeUtensilRaw(var receipeUuid: String, var utensilUuid: String)