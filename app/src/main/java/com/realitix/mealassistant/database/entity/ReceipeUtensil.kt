package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = arrayOf("receipeId", "utensilId"),
    foreignKeys = arrayOf(ForeignKey(
        entity = Receipe::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("receipeId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Utensil::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("utensilId"),
        onDelete = ForeignKey.CASCADE
    )),
    indices = arrayOf(
        Index(value=["receipeId"]),
        Index(value=["utensilId"])
    )
)
class ReceipeUtensil(var receipeId: Long, var utensilId: Long) {
}