package com.realitix.nectar.database.entity

import androidx.room.*


class ReceipeStepReceipe(receipeUuid: String, stepUuid: String): ReceipeStepReceipeRaw(receipeUuid, stepUuid) {
    @Relation(parentColumn = "receipeUuid", entityColumn = "uuid", entity = ReceipeRaw::class)
    lateinit var receipe: ReceipeWS
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
open class ReceipeStepReceipeRaw(var stepUuid: String, var receipeUuid: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeStepReceipeRaw

        if (receipeUuid != other.receipeUuid) return false
        if (stepUuid != other.stepUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = receipeUuid.hashCode()
        result = 31 * result + stepUuid.hashCode()
        return result
    }
}