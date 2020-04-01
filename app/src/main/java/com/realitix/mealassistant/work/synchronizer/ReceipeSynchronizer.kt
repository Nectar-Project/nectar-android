package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.EntityType
import java.io.File

class ReceipeSynchronizer(repository: ReceipeRepository, baseRepositoryFolder: File):
    BaseSynchronizer<ReceipeSynchronizer.ParseResult, ReceipeRepository>(repository, baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val names: Map<String, String>,
        val nbPeople: Int,
        val stars: Int,
        val tags: List<String>,
        val utensils: List<String>,
        val steps: List<Step>
    )

    class Step(
        val stepUuid: String,
        val previousStepUuid: String? = null,
        val description: String,
        val duration: Int,
        val aliments: Map<String, Int>,
        val receipes: List<String>
    )

    override fun getEntityType(): EntityType = EntityType.RECEIPE
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)

    override fun updateDb(repo: ReceipeRepository, parseResult: ParseResult) {
        // Create receipe only if not exists
        if(repo.getReceipe(parseResult.uuid) == null) {
            repo.insertReceipe(ReceipeRaw(parseResult.uuid, parseResult.nbPeople, parseResult.stars))
        }

        // names
        for((lang, name) in parseResult.names) {
            repo.insertReceipeName(ReceipeNameRaw(parseResult.uuid, lang, name))
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
                    0,
                    step.description,
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
}