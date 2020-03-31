package com.realitix.mealassistant.database.entity

import androidx.room.*

class AlimentStateMeasure(alimentStateUuid: String, measureUuid: String, quantity: Int): AlimentStateMeasureRaw(alimentStateUuid, measureUuid, quantity)

@Entity(
    primaryKeys = ["alimentStateUuid", "measureUuid"],
    foreignKeys = [
        ForeignKey(
        entity = AlimentStateRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentStateUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MeasureRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["measureUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentStateUuid"]),
        Index(value=["measureUuid"])
    ]
)
open class AlimentStateMeasureRaw (
    var alimentStateUuid: String,
    var measureUuid: String,
    // quantity in g
    var quantity: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentStateMeasureRaw

        if (alimentStateUuid != other.alimentStateUuid) return false
        if (measureUuid != other.measureUuid) return false
        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentStateUuid.hashCode()
        result = 31 * result + measureUuid.hashCode()
        result = 31 * result + quantity
        return result
    }
}