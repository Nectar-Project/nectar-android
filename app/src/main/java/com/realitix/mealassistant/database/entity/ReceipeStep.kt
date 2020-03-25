package com.realitix.mealassistant.database.entity

import androidx.room.*


class ReceipeStep(uuid: String, receipeUuid: String, order: Int, description: String, duration: Int):
    ReceipeStepRaw(uuid, receipeUuid, order, description, duration) {
    @Relation(parentColumn = "uuid", entityColumn = "stepUuid", entity = ReceipeStepAlimentRaw::class)
    var aliments: List<ReceipeStepAliment>? = null
    // Can't return list of ReceipeStepReceipe because it creates an inner loop and a stackoverflow
    @Relation(parentColumn = "uuid", entityColumn = "stepUuid", entity = ReceipeStepReceipeRaw::class)
    var receipes: List<ReceipeStepReceipe>? = null
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"])
    ]
)
open class ReceipeStepRaw(
    @PrimaryKey
    var uuid: String,
    var receipeUuid: String,
    var order: Int,
    var description: String,
    // Duration in minutes
    var duration: Int
)