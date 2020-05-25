package com.realitix.nectar.database.entity

import androidx.room.*


class ReceipeStepAlimentState(stepUuid: String, alimentStateUuid: String, weight: Int):
    ReceipeStepAlimentStateRaw(stepUuid, alimentStateUuid, weight) {
    @Relation(parentColumn = "alimentStateUuid", entityColumn = "uuid", entity = AlimentStateRaw::class)
    lateinit var alimentState: AlimentState
}

@Entity(
    primaryKeys = ["stepUuid", "alimentStateUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentStateRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentStateUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStepRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["stepUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentStateUuid"]),
        Index(value=["stepUuid"])
    ]
)
open class ReceipeStepAlimentStateRaw(
    var stepUuid: String,
    var alimentStateUuid: String,
    var weight: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeStepAlimentStateRaw

        if (alimentStateUuid != other.alimentStateUuid) return false
        if (stepUuid != other.stepUuid) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentStateUuid.hashCode()
        result = 31 * result + stepUuid.hashCode()
        result = 31 * result + weight
        return result
    }
}