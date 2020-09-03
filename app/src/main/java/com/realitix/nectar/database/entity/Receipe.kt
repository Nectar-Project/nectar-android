package com.realitix.nectar.database.entity

import androidx.room.*


class Receipe(uuid: String, nameUuid: String, portions: Int, stars: Int):
    ReceipeWS(uuid, nameUuid, portions, stars) {
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeStepRaw::class)
    lateinit var steps: List<ReceipeStep>

    fun listAliments(): Pair<AlimentWS, Int> {
        val res = mutableListOf<Pair<AlimentWS, Int>>()

        for(step in steps) {
            step.aliments
        }
    }
}

// Receipe without steps to prevent cycle in ReceipeStepReceipe
open class ReceipeWS(uuid: String, nameUuid: String, portions: Int, stars: Int):
    ReceipeRaw(uuid, nameUuid, portions, stars) {
    @Relation(parentColumn = "nameUuid", entityColumn = "uuid", entity = StringKeyRaw::class)
    lateinit var name: StringKey
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeTagRaw::class)
    lateinit var tags: List<ReceipeTag>
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeUtensilRaw::class)
    lateinit var utensils: List<ReceipeUtensil>
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeImageRaw::class)
    lateinit var images: List<ReceipeImage>
    @Relation(parentColumn = "uuid", entityColumn = "receipeUuid", entity = ReceipeMeasureRaw::class)
    lateinit var measures: List<ReceipeMeasure>

    fun getName(): String {
        return name.getValue()
    }
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = StringKeyRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["nameUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["nameUuid"])
    ]
)
open class ReceipeRaw(
    @PrimaryKey
    var uuid: String,
    var nameUuid: String,
    var portions: Int,
    var stars: Int
): UuidInterface {
    override fun getEntityUuid() = uuid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeRaw

        if (uuid != other.uuid) return false
        if (nameUuid != other.nameUuid) return false
        if (portions != other.portions) return false
        if (stars != other.stars) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nameUuid.hashCode()
        result = 31 * result + portions.hashCode()
        result = 31 * result + stars
        return result
    }

}