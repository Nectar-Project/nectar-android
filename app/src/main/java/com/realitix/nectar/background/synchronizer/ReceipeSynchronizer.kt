package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.background.DummyNotifier
import com.realitix.nectar.background.NotifierInterface
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
    baseRepositoryFolder: File,
    private val notifier: NotifierInterface = DummyNotifier()
): BaseSynchronizer<ReceipeSynchronizer.ParseResult>(baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String,
        val stars: Int,
        val measures: Map<String, Float>,
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
    override fun isEntityExists(uuid: String): Boolean = rReceipe.get(uuid) != null

    override fun fromGitDeleteInDb(uuid: String) {
        val receipe = rReceipe.get(uuid)!!

        // delete steps
        for(step in receipe.getSteps(rReceipeStep)) {
            for(a in step.aliments) {
                rReceipeStepAlimentState.delete(a)
            }

            for(r in step.receipes) {
                rReceipeStepReceipe.delete(r)
            }

            rReceipeStep.delete(step)
        }

        for(utensil in receipe.utensils) {
            rReceipeUtensil.delete(utensil)
        }

        for(tag in receipe.tags) {
            rReceipeTag.delete(tag)
        }

        for(measure in receipe.measures) {
            rReceipeMeasure.delete(measure)
        }

        rReceipe.delete(receipe)
    }

    override fun updateDb(parseResult: ParseResult) {
        // Create receipe only if not exists
        if(rReceipe.get(parseResult.uuid) == null) {
            rReceipe.insert(ReceipeRaw(parseResult.uuid, parseResult.nameUuid, parseResult.stars))
            notifier.addNotification("Ajout d'une recette")
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
            for ((alimentStateUuid, weight) in step.alimentStates) {
                if(rReceipeStepAlimentState.get(step.stepUuid, alimentStateUuid) == null) {
                    rReceipeStepAlimentState.insert(
                        ReceipeStepAlimentStateRaw(
                            step.stepUuid,
                            alimentStateUuid,
                            weight
                        )
                    )
                }
            }

            // receipes
            for ((receipeUuid, proportion) in step.receipes) {
                if(rReceipeStepReceipe.get(step.stepUuid, receipeUuid) == null) {
                    rReceipeStepReceipe.insert(ReceipeStepReceipeRaw(step.stepUuid, receipeUuid, proportion))
                }
            }
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val receipe = rReceipe.get(uuid)!!

        val measures = mutableMapOf<String, Float>()
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
        for(s in receipe.getSteps(rReceipeStep)) {
            val alimentStates = mutableMapOf<String, Int>()
            for(a in s.aliments) {
                alimentStates[a.alimentStateUuid] = a.weight
            }

            val receipes = mutableMapOf<String, Float>()
            for(r in s.receipes) {
                receipes[r.receipeUuid] = r.proportion
            }
            steps.add(Step(s.uuid, null, s.descriptionUuid, s.duration, alimentStates, receipes))
        }

        return ParseResult(receipe.uuid, receipe.nameUuid, receipe.stars, measures, tags, utensils, steps)
    }
}