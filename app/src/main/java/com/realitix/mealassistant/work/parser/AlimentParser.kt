package com.realitix.mealassistant.work.parser

import android.content.Context
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.AlimentRepository
import java.io.File
import java.io.FileReader

class AlimentParser {
    class ParseResult(
        val uuid: String,
        val names: Map<String, String>, // lang=name
        val altNames: Map<String, String>, // lang=name
        val tags: List<String>, // list of uuid
        val states: List<State> // list of states
    )
    class State(val uuid: String, val measures: MutableMap<String, Int>, val nutrition: Nutrition)

    companion object {
        fun update(context: Context, repositoryName: String, alimentUuid: String) {

        }

        fun add(context: Context, repositoryName: String, alimentUuid: String) {
            // uuid should not exist in database since it's an add but we check
            val aRepo = AlimentRepository.getInstance(context)

            // aliment exists in database, update it
            if(aRepo.getAliment(alimentUuid) != null) {
                return update(context, repositoryName, alimentUuid)
            }

            val parseResult = parse(context, repositoryName, alimentUuid)

            // add the aliment
            aRepo.insertAliment(AlimentRaw(parseResult.uuid))

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

            }
        }

        fun parse(context: Context, repositoryName: String, alimentUuid: String): ParseResult {
            val repoFolder = File(context.filesDir, repositoryName)
            val alimentFolder = File(repoFolder, "aliments")
            val alimentFile = File(alimentFolder, alimentUuid)
            val klaxon = Klaxon()
            val root = Parser().parse(FileReader(alimentFile)) as JsonObject

            // uuid
            val uuid = root.string("uuid")!!

            // names
            val names: MutableMap<String, String> = mutableMapOf()
            for(jsonName in root.obj("names")!!.entries) {
                names[jsonName.key] = jsonName.value as String
            }

            // alternative names
            val altNames: MutableMap<String, String> = mutableMapOf()
            for(jsonName in root.obj("alternative_names")!!.entries) {
                altNames[jsonName.key] = jsonName.value as String
            }

            // tags
            val tags: ArrayList<String> = ArrayList()
            for(jsonTag in root.array<String>("tags")!!) {
                tags.add(jsonTag)
            }

            // states
            val states: ArrayList<State> = ArrayList()
            for(jsonState in root.obj("states")!!.entries) {
                val stateUuid = jsonState.key
                val jsonStateValue = jsonState.value as JsonObject

                // measures
                val measures: MutableMap<String, Int> = mutableMapOf()
                for(jsonMeasure in jsonStateValue.obj("measures")!!.entries) {
                    val measureUuid = jsonMeasure.key
                    val measureValue = jsonMeasure.value as Int
                    measures[measureUuid] = measureValue
                }

                // nutrition
                val nutrition = klaxon.parseFromJsonObject<Nutrition>(jsonStateValue.obj("nutrition")!!)!!

                // add to state array
                states.add(State(stateUuid, measures, nutrition))
            }

            return ParseResult(uuid, names, altNames, tags, states)
        }
    }
}