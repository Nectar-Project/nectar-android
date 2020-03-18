package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Receipe(uuid: String, nb_people: Int, stars: Int): ReceipeWS(uuid, nb_people, stars) {
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeStepRaw::class)
    var steps: List<ReceipeStep>? = null
}

// Receipe without steps to prevent cycle in ReceipeStepReceipe
open class ReceipeWS(uuid: String, nb_people: Int, stars: Int): ReceipeRaw(uuid, nb_people, stars) {
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeNameRaw::class)
    var names: List<ReceipeName>? = null
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeTagRaw::class)
    var tags: List<ReceipeTag>? = null
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeUtensilRaw::class)
    var utensils: List<ReceipeUtensil>? = null

    fun getName(): String = getReceipeName().name
    fun getReceipeName(): ReceipeName = names!![0]
}

@Entity
open class ReceipeRaw(
    @PrimaryKey
    var uuid: String,
    var nb_people: Int,
    var stars: Int
)