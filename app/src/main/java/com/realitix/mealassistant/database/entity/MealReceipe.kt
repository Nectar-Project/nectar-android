package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = arrayOf("receipeId", "mealId"),
    foreignKeys = arrayOf(ForeignKey(
        entity = Receipe::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("receipeId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Meal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("mealId"),
        onDelete = ForeignKey.CASCADE
    )),
    indices = arrayOf(
        Index(value=["receipeId"]),
        Index(value=["mealId"])
    )
)
open class MealReceipe(var receipeId: Long, var mealId: Long) {
    constructor(): this(0, 0)
}