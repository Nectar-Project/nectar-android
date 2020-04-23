package com.realitix.nectar.database.entity

import androidx.room.*


class ReceipeMeasure(receipeUuid: String, measureUuid: String, quantity: Int):
    ReceipeMeasureRaw(receipeUuid, measureUuid, quantity)

@Entity(
    primaryKeys = ["receipeUuid", "measureUuid"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MeasureRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["measureUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["measureUuid"])
    ]
)
open class ReceipeMeasureRaw (
    var receipeUuid: String,
    var measureUuid: String,
    var quantity: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeMeasureRaw

        if (receipeUuid != other.receipeUuid) return false
        if (measureUuid != other.measureUuid) return false
        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = receipeUuid.hashCode()
        result = 31 * result + measureUuid.hashCode()
        result = 31 * result + quantity
        return result
    }

}
