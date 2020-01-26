package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = arrayOf("alimentId", "mealId"),
    foreignKeys = arrayOf(ForeignKey(
        entity = Aliment::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("alimentId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Meal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("mealId"),
        onDelete = ForeignKey.CASCADE
    ))
)
open class MealAliment(var alimentId: Long, var mealId: Long, var quantity: Int) {
    constructor(): this(0, 0, 0)
}