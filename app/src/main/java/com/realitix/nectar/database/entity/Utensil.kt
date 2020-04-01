package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Utensil(uuid: String): UtensilRaw(uuid) {
    @Relation(parentColumn = "uuid", entityColumn = "utensilUuid", entity = UtensilNameRaw::class)
    var names: List<UtensilName>? = null
}

@Entity
open class UtensilRaw(
    @PrimaryKey
    var uuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UtensilRaw

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}