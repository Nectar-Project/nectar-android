package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation


class MealAliment(alimentUuid: String, mealUuid: String, quantity: Int): MealAlimentRaw(alimentUuid, mealUuid, quantity) {
    @Relation(parentColumn = "alimentUuid", entityColumn = "uuid",  entity = AlimentRaw::class)
    lateinit var aliment: Aliment
}

@Entity(
    primaryKeys = ["alimentUuid", "mealUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MealRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["mealUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"]),
        Index(value=["mealUuid"])
    ]
)
open class MealAlimentRaw(var alimentUuid: String, var mealUuid: String, var quantity: Int)