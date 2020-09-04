package com.realitix.nectar.database.entity

import android.content.Context
import androidx.room.*
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.util.NectarUtil


// Receipe without steps to prevent cycle in ReceipeStepReceipe
class Receipe(uuid: String, nameUuid: String, portions: Int, stars: Int):
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

    // Bug with room preventing nested relation
    fun getSteps(rReceipeStep: ReceipeStepRepository): List<ReceipeStep> = rReceipeStep.listByReceipe(uuid)
    fun getName(): String = name.getValue()

    /*fun listAliments(): List<Pair<AlimentWS, Int>> {
        val out = mutableListOf<Pair<AlimentWS, Int>>()

        for(step in steps) {
            for(aliment in step.aliments) {
                NectarUtil.addAlimentToList(out, aliment.alimentState.aliment, aliment.weight)
            }

            for(receipe in step.receipes) {
                // for(a in receipe.rece .listAliments()) {

                //}
            }
        }

        return out
    }*/
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