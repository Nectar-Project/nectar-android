package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Meal(var timestamp: Long, var nb_people: Int, var description: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(): this(0, 0, "")
}