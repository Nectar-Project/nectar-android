package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Receipe(uuid: String, nb_people: Int, stars: Int): ReceipeWS(uuid, nb_people, stars) {
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeStepRaw::class)
    lateinit var steps: List<ReceipeStep>
}

// Receipe without steps to prevent cycle in ReceipeStepReceipe
open class ReceipeWS(uuid: String, nb_people: Int, stars: Int): ReceipeRaw(uuid, nb_people, stars) {
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeNameRaw::class)
    lateinit var names: List<ReceipeName>
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeTagRaw::class)
    lateinit var tags: List<ReceipeTag>
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeUtensilRaw::class)
    lateinit var utensils: List<ReceipeUtensil>
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeImageRaw::class)
    lateinit var images: List<ReceipeImage>

    fun getName(): String = getReceipeName().name
    fun getReceipeName(): ReceipeName = names[0]
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