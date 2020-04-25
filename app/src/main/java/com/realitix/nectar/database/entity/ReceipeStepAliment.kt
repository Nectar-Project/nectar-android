package com.realitix.nectar.database.entity

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
open class ReceipeStepAlimentRaw(var stepUuid: String, var alimentUuid: String, var quantity: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeStepAlimentRaw

        if (alimentUuid != other.alimentUuid) return false
        if (stepUuid != other.stepUuid) return false
        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentUuid.hashCode()
        result = 31 * result + stepUuid.hashCode()
        result = 31 * result + quantity
        return result
    }
}