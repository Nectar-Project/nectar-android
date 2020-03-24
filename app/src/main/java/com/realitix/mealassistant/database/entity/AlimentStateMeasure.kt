package com.realitix.mealassistant.database.entity

import androidx.room.*

class AlimentStateMeasure(alimentStateId: Int, measureUuid: String, quantity: Int): AlimentStateMeasureRaw(alimentStateId, measureUuid, quantity)

@Entity(
    primaryKeys = ["alimentStateId", "measureUuid"],
    foreignKeys = [
        ForeignKey(
        entity = AlimentStateRaw::class,
        parentColumns = ["id"],
        childColumns = ["alimentStateId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MeasureRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["measureUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentStateId"]),
        Index(value=["measureUuid"])
    ]
)
open class AlimentStateMeasureRaw (
    var alimentStateId: Int,
    var measureUuid: String,
    var quantity: Int
)