package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.EntityType
import java.io.File

class ReceipeSynchronizer(
    private val rReceipe: ReceipeRepository,
    private val rReceipeMeasure: ReceipeMeasureRepository,
    private val rReceipeTag: ReceipeTagRepository,
    private val rReceipeUtensil: ReceipeUtensilRepository,
    private val rReceipeStep: ReceipeStepRepository,
    private val rReceipeStepAlimentState: ReceipeStepAlimentStateRepository,
    private val rReceipeStepReceipe: ReceipeStepReceipeRepository,
    baseRepositoryFolder: File
): BaseSynchronizer<ReceipeSynchronizer.ParseResult>(baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String,
        val portions: Int,
        val stars: Int,
        val measures: Map<String, Int>,
        val tags: List<String>,
        val utensils: List<String>,
        val steps: List<Step>
    )

    class Step(
        val stepUuid: String,
        val previousStepUuid: String? = null,
        val descriptionUuid: String,
        val duration: Int,
        val alimentStates: Map<String, Int>,
        val receipes: Map<String, Float>
    )

    override fun getEntityType(): EntityType = EntityType.RECEIPE
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)

    override fun updateDb(parseResult: ParseResult) {
        // Create receipe only if not exists
        if(rReceipe.get(parseResult.uuid) == null) {
            rReceipe.insert(ReceipeRaw(parseResult.uuid, parseResult.nameUuid, parseResult.portions, parseResult.stars))
        }

        // measures
        for((measureUuid, quantity) in parseResult.measures) {
            if(rReceipeMeasure.get(parseResult.uuid, measureUuid) == null) {
                rReceipeMeasure.insert(ReceipeMeasureRaw(parseResult.uuid, measureUuid, quantity))
            }
        }

        // tags
        for(tagUuid in parseResult.tags) {
            if(rReceipeTag.get(parseResult.uuid, tagUuid) == null) {
                rReceipeTag.insert(ReceipeTagRaw(parseResult.uuid, tagUuid))
            }
        }

        // utensils
        for(utensilUid in parseResult.utensils) {
            if(rReceipeUtensil.get(parseResult.uuid, utensilUid) == null) {
                rReceipeUtensil.insert(ReceipeUtensilRaw(parseResult.uuid, utensilUid))
            }
        }

        // steps
        for(step in parseResult.steps) {
            if(rReceipeStep.get(step.stepUuid) == null) {
                rReceipeStep.insert(
                    ReceipeStepRaw(
                        step.stepUuid,
                        parseResult.uuid,
                        step.previousStepUuid,
                        step.descriptionUuid,
                        step.duration
                    )
                )
            }
            // aliments
            for ((alimentStateUuid, quantity) in step.alimentStates) {
                if(rReceipeStepAlimentState.get(step.stepUuid, alimentStateUuid) == null) {
                    rReceipeStepAlimentState.insert(
                        ReceipeStepAlimentStateRaw(
                            step.stepUuid,
                            alimentStateUuid,
                            quantity
                        )
                    )
                }
            }

            // receipes
            for ((receipeUuid, quantity) in step.receipes) {
                if(rReceipeStepReceipe.get(step.stepUuid, receipeUuid) == null) {
                    rReceipeStepReceipe.insert(ReceipeStepReceipeRaw(step.stepUuid, receipeUuid, quantity))
                }
            }
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val receipe = rReceipe.get(uuid)!!

        val measures = mutableMapOf<String, Int>()
        for(a in receipe.measures) {
            measures[a.measureUuid] = a.quantity
        }

        val tags = mutableListOf<String>()
        for(a in receipe.tags) {
            tags.add(a.tagUuid)
        }

        val utensils = mutableListOf<String>()
        for(a in receipe.utensils) {
            tags.add(a.utensilUuid)
        }

        val steps = mutableListOf<Step>()
        for(s in receipe.steps) {
            val alimentStates = mutableMapOf<String, Int>()
            for(a in s.aliments) {
                alimentStates[a.alimentStateUuid] = a.weight
            }

            val receipes = mutableMapOf<String, Float>()
            for(r in s.receipes) {
                receipes[r.receipeUuid] = r.portions
            }
            steps.add(Step(s.uuid, null, s.descriptionUuid, s.duration, alimentStates, receipes))
        }

        return ParseResult(receipe.uuid, receipe.nameUuid, receipe.portions, receipe.stars, measures, tags, utensils, steps)
    }
}