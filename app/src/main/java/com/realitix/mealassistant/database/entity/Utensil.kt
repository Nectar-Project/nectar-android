package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


class Utensil(name: String): UtensilRaw(name)

@Entity
open class UtensilRaw(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}