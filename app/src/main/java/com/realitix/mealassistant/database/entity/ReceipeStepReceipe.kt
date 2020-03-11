package com.realitix.mealassistant.database.entity

import androidx.room.*


class ReceipeStepReceipe(receipeId: Long, stepId: Long): ReceipeStepReceipeRaw(receipeId, stepId) {
    @Relation(parentColumn = "receipeId", entityColumn = "id", entity = ReceipeRaw::class)
    lateinit var receipe: ReceipeRaw
}

@Entity(
    primaryKeys = ["stepId", "receipeId"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("receipeId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStepRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("stepId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeId"]),
        Index(value=["stepId"])
    ]
)
open class ReceipeStepReceipeRaw(var receipeId: Long, var stepId: Long)