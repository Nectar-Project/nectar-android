package com.realitix.nectar.database.entity

import androidx.room.*
import com.realitix.nectar.repository.ReceipeStepRepository
import com.realitix.nectar.util.NectarUtil
import kotlinx.coroutines.runBlocking


// Receipe without steps to prevent cycle in ReceipeStepReceipe
class Receipe(uuid: String, nameUuid: String, stars: Int):
    ReceipeRaw(uuid, nameUuid, stars) {
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
    fun getSteps(rReceipeStep: ReceipeStepRepository): List<ReceipeStep> {
        var res = listOf<ReceipeStep>()
        runBlocking {
            res = rReceipeStep.listByReceipeSuspend(uuid)
        }

        return res
    }

    fun getName(): String = name.getValue()

    fun listAliments(receipeStepRepository: ReceipeStepRepository, proportion: Float = 1f): List<Pair<AlimentState, Int>> {
        val out = mutableListOf<Pair<AlimentState, Int>>()

        for(step in getSteps(receipeStepRepository)) {
            for(aliment in step.aliments) {
                NectarUtil.addAlimentStateToList(out, aliment.alimentState, (aliment.weight*proportion).toInt())
            }

            for(receipe in step.receipes) {
                val p = receipe.proportion * proportion
                NectarUtil.addAlimentStateListToList(out, receipe.receipe.listAliments(receipeStepRepository, p))
            }
        }

        return out
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
    var stars: Int
): UuidInterface {
    override fun getEntityUuid() = uuid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeRaw

        if (uuid != other.uuid) return false
        if (nameUuid != other.nameUuid) return false
        if (stars != other.stars) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nameUuid.hashCode()
        result = 31 * result + stars
        return result
    }

}