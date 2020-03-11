package com.realitix.mealassistant.database.entity

import androidx.room.*


class ReceipeStepAliment(alimentId: Long, stepId: Long, quantity: Int): ReceipeStepAlimentRaw(alimentId, stepId, quantity) {
    @Relation(parentColumn = "alimentId", entityColumn = "id", entity = AlimentRaw::class)
    lateinit var aliment: Aliment
}

@Entity(
    primaryKeys = ["stepId", "alimentId"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("alimentId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStepRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("stepId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentId"]),
        Index(value=["stepId"])
    ]
)
open class ReceipeStepAlimentRaw(var alimentId: Long, var stepId: Long, var quantity: Int)