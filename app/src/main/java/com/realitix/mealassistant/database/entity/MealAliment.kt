package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation


class MealAliment(alimentId: Long, mealId: Long, quantity: Int): MealAlimentRaw(alimentId, mealId, quantity) {
    @Relation(parentColumn = "alimentId", entityColumn = "id",  entity = AlimentRaw::class)
    lateinit var aliment: Aliment
}

@Entity(
    primaryKeys = ["alimentId", "mealId"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("alimentId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MealRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("mealId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentId"]),
        Index(value=["mealId"])
    ]
)
open class MealAlimentRaw(var alimentId: Long, var mealId: Long, var quantity: Int)