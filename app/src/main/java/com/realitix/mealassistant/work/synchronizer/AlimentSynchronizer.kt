package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.MealUtil
import java.io.File
import java.io.FileReader

class AlimentSynchronizer: BaseSynchronizer<AlimentSynchronizer.ParseResult, AlimentRepository>() {
    class ParseResult(
        val uuid: String,
        val names: Map<String, String>, // lang=name
        val altNames: Map<String, String>, // lang=name
        val tags: List<String>, // list of uuid
        val states: List<State> // list of states
    )

    class State(val uuid: String, val measures: Map<String, Int>, val nutrition: Nutrition)

    override fun getEntityType(): EntityType = EntityType.ALIMENT
    override fun getRepository(context: Context): AlimentRepository = AlimentRepository.getInstance(context)

    override fun fromGitToDb(context: Context, repositoryName: String, uuid: String) {
        val aRepo = getRepository(context)
        val parseResult = getParseResult<ParseResult>(context, repositoryName, uuid)

        // Create aliment only if not exists
        if(aRepo.getAliment(parseResult.uuid) == null) {
            aRepo.insertAliment(AlimentRaw(parseResult.uuid))
        }

        // add names
        for((lang, name) in parseResult.names) {
            aRepo.insertAlimentName(AlimentNameRaw(parseResult.uuid, lang, name))
        }

        // TODO add alternative names

        // tags
        for(tagUuid in parseResult.tags) {
            aRepo.insertAlimentTag(AlimentTagRaw(parseResult.uuid, tagUuid))
        }

        // states
        for(state in parseResult.states) {
            // nutrition
            val alimentStateUuid = MealUtil.generateUuid()
            aRepo.insertAlimentState(AlimentStateRaw(alimentStateUuid, parseResult.uuid, state.uuid, state.nutrition))
            for((measureUuid, quantity) in state.measures) {
                aRepo.insertAlimentStateMeasure(AlimentStateMeasureRaw(alimentStateUuid, measureUuid, quantity))
            }
        }
    }
}