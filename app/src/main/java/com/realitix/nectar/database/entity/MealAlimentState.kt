package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation


class MealAlimentState(mealUuid: String, alimentStateUuid: String, weight: Int):
    MealAlimentStateRaw(mealUuid, alimentStateUuid, weight) {
    @Relation(parentColumn = "alimentStateUuid", entityColumn = "uuid",  entity = AlimentStateRaw::class)
    lateinit var alimentState: AlimentState
}

@Entity(
    primaryKeys = ["alimentStateUuid", "mealUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentStateRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentStateUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MealRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["mealUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentStateUuid"]),
        Index(value=["mealUuid"])
    ]
)
open class MealAlimentStateRaw(var mealUuid: String, var alimentStateUuid: String, var weight: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealAlimentStateRaw

        if (alimentStateUuid != other.alimentStateUuid) return false
        if (mealUuid != other.mealUuid) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentStateUuid.hashCode()
        result = 31 * result + mealUuid.hashCode()
        result = 31 * result + weight
        return result
    }
}