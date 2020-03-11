package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation


class MealReceipe(receipeId: Long, mealId: Long): MealReceipeRaw(receipeId, mealId) {
    @Relation(parentColumn = "receipeId", entityColumn = "id", entity = ReceipeRaw::class)
    lateinit var receipe: Receipe
}

@Entity(
    primaryKeys = ["receipeId", "mealId"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("receipeId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MealRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("mealId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeId"]),
        Index(value=["mealId"])
    ]
)
open class MealReceipeRaw(var receipeId: Long, var mealId: Long)