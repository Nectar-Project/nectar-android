package com.realitix.mealassistant.database.entity

import androidx.room.*


class ReceipeStepReceipe(receipeUuid: String, stepUuid: String): ReceipeStepReceipeRaw(receipeUuid, stepUuid) {
    @Relation(parentColumn = "receipeUuid", entityColumn = "uuid", entity = ReceipeRaw::class)
    lateinit var receipe: ReceipeRaw
}

@Entity(
    primaryKeys = ["stepUuid", "receipeUuid"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStepRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["stepUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["stepUuid"])
    ]
)
open class ReceipeStepReceipeRaw(var receipeUuid: String, var stepUuid: String)