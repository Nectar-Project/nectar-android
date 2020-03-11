package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class ReceipeUtensil(receipeId: Long, utensilId: Long): ReceipeUtensilRaw(receipeId, utensilId)

@Entity(
    primaryKeys = ["receipeId", "utensilId"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("receipeId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UtensilRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("utensilId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeId"]),
        Index(value=["utensilId"])
    ]
)
open class ReceipeUtensilRaw(var receipeId: Long, var utensilId: Long)