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
)