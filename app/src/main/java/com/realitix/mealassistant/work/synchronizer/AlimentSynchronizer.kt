package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.MealUtil
import com.realitix.mealassistant.util.UuidGeneratorInterface
import java.io.File
import java.io.FileReader

class AlimentSynchronizer(context: Context, repository: AlimentRepository, val uuidGenerator: UuidGeneratorInterface):
    BaseSynchronizer<AlimentSynchronizer.ParseResult, AlimentRepository>(context, repository) {
    class ParseResult(
        val uuid: String,
        val names: Map<String, String>,
        val images: List<String>,
        val tags: List<String>,
        val states: Map<String, State>
    )

    class State(val measures: Map<String, Int>, val nutrition: Nutrition)

    override fun getEntityType(): EntityType = EntityType.ALIMENT
    override fun getParseResult(context: Context, repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(context, repositoryName, uuid)

    override fun updateDb(repo: AlimentRepository, parseResult: ParseResult) {
        // Create aliment only if not exists
        if(repo.getAliment(parseResult.uuid) == null) {
            repo.insertAliment(AlimentRaw(parseResult.uuid))
        }

        // names
        for((lang, name) in parseResult.names) {
            repo.insertAlimentName(AlimentNameRaw(parseResult.uuid, lang, name))
        }

        // images
        for(imageUuid in parseResult.images) {
            repo.insertAlimentImage(AlimentImageRaw(parseResult.uuid, imageUuid))
        }

        // tags
        for(tagUuid in parseResult.tags) {
            repo.insertAlimentTag(AlimentTagRaw(parseResult.uuid, tagUuid))
        }

        // states
        for((stateUuid, state) in parseResult.states) {
            // nutrition
            val alimentStateUuid = uuidGenerator.generateUuid()
            repo.insertAlimentState(AlimentStateRaw(alimentStateUuid, parseResult.uuid, stateUuid, state.nutrition))
            for((measureUuid, quantity) in state.measures) {
                repo.insertAlimentStateMeasure(AlimentStateMeasureRaw(alimentStateUuid, measureUuid, quantity))
            }
        }
    }
}