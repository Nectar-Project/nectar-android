package com.realitix.nectar.database.entity

import android.util.Log
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

    fun getStepsOrdered(rReceipeStep: ReceipeStepRepository): List<ReceipeStep> =
        recursiveAddOrdered(rReceipeStep, getSteps(rReceipeStep).toMutableList(), null)

    private fun recursiveAddOrdered(
        rReceipeStep: ReceipeStepRepository,
        stepsToTreat: MutableList<ReceipeStep>,
        currentStep: ReceipeStep?): List<ReceipeStep> {
        val outSteps = mutableListOf<ReceipeStep>()
        val depSteps = stepsToTreat.filter { it.getPreviousStep(rReceipeStep) == currentStep }
        stepsToTreat.removeAll(depSteps)
        for(s in depSteps) {
            outSteps.add(s)
            outSteps.addAll(recursiveAddOrdered(rReceipeStep, stepsToTreat, s))
        }

        return outSteps
    }

    // Return all steps and steps in receipe
    // The previous step is set to the dependancy step
    private fun getStepsRecursive(rReceipeStep: ReceipeStepRepository, receipe: Receipe): List<ReceipeStep> {
        val steps = receipe.getSteps(rReceipeStep).toMutableList()
        for(step in steps) {
            for(r in step.receipes) {
                // All dependancy steps take this step as dependancy
                val dependancySteps = getStepsRecursive(rReceipeStep, r.receipe)
                for(ds in dependancySteps) {
                    if(ds.previousStepUuid == null) {
                        ds.previousStepUuid = step.uuid
                    }
                }
                steps.addAll(dependancySteps)
            }
        }

        return steps
    }

    fun getStepsWithTimestamp(rReceipeStep: ReceipeStepRepository, startTimestamp: Long): List<Pair<Long, ReceipeStep>> {
        val steps = getStepsRecursive(rReceipeStep, this)
        val stepsTotalDuration = mutableListOf<Pair<Int, ReceipeStep>>()

        for(step in steps) {
            val duration = getAccumulateStepDuration(steps, step)
            stepsTotalDuration.add(duration to step)
        }

        val stepsWithTimestamp = mutableListOf<Pair<Long, ReceipeStep>>()
        for(s in stepsTotalDuration) {
            stepsWithTimestamp.add(startTimestamp - s.first to s.second)
        }

        stepsWithTimestamp.sortBy { it.first }

        return stepsWithTimestamp
    }

    // Return the duration of the step with accumulation of the next step duration
    private fun getAccumulateStepDuration(allSteps: List<ReceipeStep>, currentStep: ReceipeStep): Int {
        // Get all depending steps
        val depSteps = allSteps.filter { it.uuid == currentStep.previousStepUuid }
        val depStep = if(depSteps.isNotEmpty()) depSteps[0] else null
        val depDuration = if(depStep == null) 0 else getAccumulateStepDuration(allSteps, depStep)

        return currentStep.duration + depDuration
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