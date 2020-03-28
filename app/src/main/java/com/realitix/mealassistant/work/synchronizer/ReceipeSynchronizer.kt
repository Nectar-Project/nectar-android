package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.EntityType
import java.io.File
import java.io.FileReader

class ReceipeSynchronizer(context: Context, repository: ReceipeRepository):
    BaseSynchronizer<ReceipeSynchronizer.ParseResult, ReceipeRepository>(context, repository) {
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
        val uuid: String,
        val previousStepUuid: String,
        val description: String,
        val duration: Int,
        val aliments: Map<String, Int>,
        val receipes: List<String>
    )

    override fun getEntityType(): EntityType = EntityType.RECEIPE
    override fun getParseResult(context: Context, repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(context, repositoryName, uuid)

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
                    step.uuid,
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
                        step.uuid,
                        quantity
                    )
                )
            }

            // receipes
            for (receipeUuid in step.receipes) {
                repo.insertReceipeStepReceipe(ReceipeStepReceipeRaw(receipeUuid, step.uuid))
            }
        }
    }
}