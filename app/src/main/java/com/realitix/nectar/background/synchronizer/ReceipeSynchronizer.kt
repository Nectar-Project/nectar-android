package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.ReceipeRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class ReceipeSynchronizer(repository: ReceipeRepository, baseRepositoryFolder: File):
    BaseSynchronizer<ReceipeSynchronizer.ParseResult, ReceipeRepository>(repository, baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String,
        val nbPeople: Int,
        val stars: Int,
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

    override fun updateDb(repo: ReceipeRepository, parseResult: ParseResult) {
        // Create receipe only if not exists
        if(repo.getReceipe(parseResult.uuid) == null) {
            repo.insertReceipe(ReceipeRaw(parseResult.uuid, parseResult.nameUuid, parseResult.nbPeople, parseResult.stars))
        }

        // tags
        for(tagUuid in parseResult.tags) {
            repo.insertReceipeTag(ReceipeTagRaw(parseResult.uuid, tagUuid))
        }

        // utensils
        for(utensilUid in parseResult.utensils) {
            repo.insertReceipeUtensil(ReceipeUtensilRaw(parseResult.uuid, utensilUid))
        }

        // steps
        for(step in parseResult.steps) {
            repo.insertReceipeStep(
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
                repo.insertReceipeStepAliment(
                    ReceipeStepAlimentRaw(
                        alimentUuid,
                        step.stepUuid,
                        quantity
                    )
                )
            }

            // receipes
            for (receipeUuid in step.receipes) {
                repo.insertReceipeStepReceipe(ReceipeStepReceipeRaw(receipeUuid, step.stepUuid))
            }
        }
    }

    override fun populateParseResult(repo: ReceipeRepository, uuid: String): ParseResult {
        val receipe = repo.getReceipe(uuid)!!

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

        return ParseResult(receipe.uuid, receipe.nameUuid, receipe.nbPeople, receipe.stars, tags, utensils, steps)
    }
}