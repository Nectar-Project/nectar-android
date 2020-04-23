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
    private val rReceipeStepAliment: ReceipeStepAlimentRepository,
    private val rReceipeStepReceipe: ReceipeStepReceipeRepository,
    baseRepositoryFolder: File
): BaseSynchronizer<ReceipeSynchronizer.ParseResult>(baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String,
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
        val aliments: Map<String, Int>,
        val receipes: List<String>
    )

    override fun getEntityType(): EntityType = EntityType.RECEIPE
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)

    override fun updateDb(parseResult: ParseResult) {
        // Create receipe only if not exists
        if(rReceipe.get(parseResult.uuid) == null) {
            rReceipe.insert(ReceipeRaw(parseResult.uuid, parseResult.nameUuid, parseResult.stars))
        }

        // measures
        for((measureUuid, quantity) in parseResult.measures) {
            rReceipeMeasure.insert(ReceipeMeasureRaw(parseResult.uuid, measureUuid, quantity))
        }

        // tags
        for(tagUuid in parseResult.tags) {
            rReceipeTag.insert(ReceipeTagRaw(parseResult.uuid, tagUuid))
        }

        // utensils
        for(utensilUid in parseResult.utensils) {
            rReceipeUtensil.insert(ReceipeUtensilRaw(parseResult.uuid, utensilUid))
        }

        // steps
        for(step in parseResult.steps) {
            rReceipeStep.insert(
                ReceipeStepRaw(
                    step.stepUuid,
                    parseResult.uuid,
                    step.previousStepUuid,
                    step.descriptionUuid,
                    step.duration
                )
            )
            // aliments
            for ((alimentUuid, quantity) in step.aliments) {
                rReceipeStepAliment.insert(
                    ReceipeStepAlimentRaw(
                        alimentUuid,
                        step.stepUuid,
                        quantity
                    )
                )
            }

            // receipes
            for (receipeUuid in step.receipes) {
                rReceipeStepReceipe.insert(ReceipeStepReceipeRaw(receipeUuid, step.stepUuid))
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
            val aliments = mutableMapOf<String, Int>()
            for(a in s.aliments) {
                aliments[a.alimentUuid] = a.quantity
            }

            val receipes = mutableListOf<String>()
            for(r in s.receipes) {
                receipes.add(r.receipeUuid)
            }
            steps.add(Step(s.uuid, null, s.descriptionUuid, s.duration, aliments, receipes))
        }

        return ParseResult(receipe.uuid, receipe.nameUuid, receipe.stars, measures, tags, utensils, steps)
    }
}