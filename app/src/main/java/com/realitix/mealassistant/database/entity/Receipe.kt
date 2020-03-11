package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Receipe(name: String, nb_people: Int, stars: Int): ReceipeRaw(name, nb_people, stars) {
    @Relation(parentColumn = "id", entityColumn = "receipeId", entity = ReceipeStepRaw::class)
    var steps: List<ReceipeStep>? = null
}

@Entity
open class ReceipeRaw(var name: String, var nb_people: Int, var stars: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}