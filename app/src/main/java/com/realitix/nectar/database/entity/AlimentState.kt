package com.realitix.nectar.database.entity

import androidx.room.*


class AlimentState(uuid: String, alimentUuid: String, stateUuid: String, nutrition: Nutrition):
    AlimentStateRaw(uuid, alimentUuid, stateUuid, nutrition) {
    @Relation(parentColumn = "uuid", entityColumn = "alimentStateUuid", entity = AlimentStateMeasureRaw::class)
    lateinit var measures: List<AlimentStateMeasure>
    @Relation(parentColumn = "stateUuid", entityColumn = "uuid", entity = StateRaw::class)
    lateinit var state: State
    @Relation(parentColumn = "alimentUuid", entityColumn = "uuid", entity = AlimentRaw::class)
    lateinit var aliment: AlimentWS
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentStateRaw

        if (uuid != other.uuid) return false
        if (alimentUuid != other.alimentUuid) return false
        if (stateUuid != other.stateUuid) return false
        if (nutrition != other.nutrition) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + alimentUuid.hashCode()
        result = 31 * result + stateUuid.hashCode()
        result = 31 * result + nutrition.hashCode()
        return result
    }
}
