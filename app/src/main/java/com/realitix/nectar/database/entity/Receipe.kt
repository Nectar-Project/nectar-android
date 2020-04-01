package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Receipe(uuid: String, nbPeople: Int, stars: Int): ReceipeWS(uuid, nbPeople, stars) {
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeStepRaw::class)
    lateinit var steps: List<ReceipeStep>
}

// Receipe without steps to prevent cycle in ReceipeStepReceipe
open class ReceipeWS(uuid: String, nbPeople: Int, stars: Int): ReceipeRaw(uuid, nbPeople, stars) {
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
    var nbPeople: Int,
    var stars: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeRaw

        if (uuid != other.uuid) return false
        if (nbPeople != other.nbPeople) return false
        if (stars != other.stars) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nbPeople
        result = 31 * result + stars
        return result
    }
}