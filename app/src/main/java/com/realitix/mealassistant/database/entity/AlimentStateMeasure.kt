package com.realitix.mealassistant.database.entity

import androidx.room.*

class AlimentStateMeasure(alimentStateUuid: String, measureUuid: String, quantity: Int): AlimentStateMeasureRaw(alimentStateUuid, measureUuid, quantity)

@Entity(
    primaryKeys = ["alimentStateUuid", "measureUuid"],
    foreignKeys = [
        ForeignKey(
        entity = AlimentStateRaw::class,
        parentColumns = arrayOf("uuid"),
        childColumns = arrayOf("alimentUuid"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MeasureRaw::class,
        parentColumns = arrayOf("uuid"),
        childColumns = arrayOf("measureUuid"),
        onDelete = ForeignKey.CASCADE
    )]
)
open class AlimentStateMeasureRaw (
    var alimentStateUuid: String,
    var measureUuid: String,
    var quantity: Int
)