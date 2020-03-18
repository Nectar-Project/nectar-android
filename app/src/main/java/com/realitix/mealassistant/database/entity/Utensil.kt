package com.realitix.mealassistant.database.entity

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
)