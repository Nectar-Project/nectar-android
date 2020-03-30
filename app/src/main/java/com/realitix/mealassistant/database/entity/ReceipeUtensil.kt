package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class ReceipeUtensil(receipeUuid: String, utensilUuid: String): ReceipeUtensilRaw(receipeUuid, utensilUuid)

@Entity(
    primaryKeys = ["receipeUuid", "utensilUuid"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UtensilRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["utensilUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["utensilUuid"])
    ]
)
open class ReceipeUtensilRaw(var receipeUuid: String, var utensilUuid: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeUtensilRaw

        if (receipeUuid != other.receipeUuid) return false
        if (utensilUuid != other.utensilUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = receipeUuid.hashCode()
        result = 31 * result + utensilUuid.hashCode()
        return result
    }
}