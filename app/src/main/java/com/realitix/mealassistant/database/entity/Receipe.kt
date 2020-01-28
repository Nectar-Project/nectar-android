package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
open class Receipe(var name: String, var nb_people: Int, var stars: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(): this("", 0, 0)
}