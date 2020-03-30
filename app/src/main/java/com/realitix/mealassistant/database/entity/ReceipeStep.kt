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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeStepRaw

        if (uuid != other.uuid) return false
        if (receipeUuid != other.receipeUuid) return false
        if (order != other.order) return false
        if (description != other.description) return false
        if (duration != other.duration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + receipeUuid.hashCode()
        result = 31 * result + order
        result = 31 * result + description.hashCode()
        result = 31 * result + duration
        return result
    }
}