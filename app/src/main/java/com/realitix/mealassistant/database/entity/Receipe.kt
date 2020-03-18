package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Receipe(uuid: String, name: String, nb_people: Int, stars: Int): ReceipeRaw(uuid, name, nb_people, stars) {
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeStepRaw::class)
    var steps: List<ReceipeStep>? = null
}

@Entity
open class ReceipeRaw(
    @PrimaryKey
    var uuid: String,
    var name: String,
    var nb_people: Int,
    var stars: Int)