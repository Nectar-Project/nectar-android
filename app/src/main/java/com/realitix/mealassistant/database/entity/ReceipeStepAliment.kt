package com.realitix.mealassistant.database.entity

import androidx.room.*


class ReceipeStepAliment(alimentUuid: String, stepUuid: String, quantity: Int): ReceipeStepAlimentRaw(alimentUuid, stepUuid, quantity) {
    @Relation(parentColumn = "alimentUuid", entityColumn = "uuid", entity = AlimentRaw::class)
    lateinit var aliment: Aliment
}

@Entity(
    primaryKeys = ["stepUuid", "alimentUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStepRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["stepUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"]),
        Index(value=["stepUuid"])
    ]
)
open class ReceipeStepAlimentRaw(var alimentUuid: String, var stepUuid: String, var quantity: Int)