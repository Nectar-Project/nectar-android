package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation


class MealReceipe(receipeUuid: String, mealUuid: String, proportion: Float):
    MealReceipeRaw(receipeUuid, mealUuid, proportion) {
    @Relation(parentColumn = "receipeUuid", entityColumn = "uuid", entity = ReceipeRaw::class)
    lateinit var receipe: Receipe
}

@Entity(
    primaryKeys = ["receipeUuid", "mealUuid"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MealRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["mealUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["mealUuid"])
    ]
)
open class MealReceipeRaw(
    var mealUuid: String,
    var receipeUuid: String,
    var proportion: Float
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealReceipeRaw

        if (receipeUuid != other.receipeUuid) return false
        if (mealUuid != other.mealUuid) return false
        if (proportion != other.proportion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = receipeUuid.hashCode()
        result = 31 * result + mealUuid.hashCode()
        result = 31 * result + proportion.hashCode()
        return result
    }
}