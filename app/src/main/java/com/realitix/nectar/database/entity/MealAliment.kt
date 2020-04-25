package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Relation


class MealAliment(alimentUuid: String, mealUuid: String, quantity: Int): MealAlimentRaw(alimentUuid, mealUuid, quantity) {
    @Relation(parentColumn = "alimentUuid", entityColumn = "uuid",  entity = AlimentRaw::class)
    lateinit var aliment: Aliment
}

@Entity(
    primaryKeys = ["alimentUuid", "mealUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MealRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["mealUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"]),
        Index(value=["mealUuid"])
    ]
)
open class MealAlimentRaw(var mealUuid: String, var alimentUuid: String, var quantity: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealAlimentRaw

        if (alimentUuid != other.alimentUuid) return false
        if (mealUuid != other.mealUuid) return false
        if (quantity != other.quantity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentUuid.hashCode()
        result = 31 * result + mealUuid.hashCode()
        result = 31 * result + quantity
        return result
    }
}