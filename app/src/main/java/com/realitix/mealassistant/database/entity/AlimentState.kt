package com.realitix.mealassistant.database.entity

import androidx.room.*


class AlimentState(uuid: String, alimentUuid: String, stateUuid: String, nutrition: Nutrition): AlimentStateRaw(uuid, alimentUuid, stateUuid, nutrition) {
    @Relation(parentColumn = "uuid", entityColumn = "alimentStateUuid", entity = AlimentStateMeasureRaw::class)
    var measures: List<AlimentStateMeasure>? = null
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = StateRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["stateUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid", "stateUuid"], unique=true),
        Index(value=["alimentUuid"]),
        Index(value=["stateUuid"])
    ]
)
open class AlimentStateRaw (
    @PrimaryKey
    var uuid: String,
    var alimentUuid: String,
    var stateUuid: String,
    @Embedded(prefix="nutrition_")
    var nutrition: Nutrition
)
