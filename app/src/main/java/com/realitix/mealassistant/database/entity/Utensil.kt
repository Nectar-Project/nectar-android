package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


class Utensil(uuid: String, name: String): UtensilRaw(uuid, name)

@Entity
open class UtensilRaw(
    @PrimaryKey
    var uuid: String,
    var name: String
)