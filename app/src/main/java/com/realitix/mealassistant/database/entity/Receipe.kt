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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeRaw

        if (uuid != other.uuid) return false
        if (nb_people != other.nb_people) return false
        if (stars != other.stars) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nb_people
        result = 31 * result + stars
        return result
    }
}