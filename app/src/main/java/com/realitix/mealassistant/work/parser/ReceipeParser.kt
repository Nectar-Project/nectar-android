package com.realitix.mealassistant.work.parser

import android.content.Context
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.ReceipeRepository
import java.io.File
import java.io.FileReader

class ReceipeParser: ParserInterface {
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

    override fun update(context: Context, repositoryName: String, uuid: String) {
        val repo = ReceipeRepository.getInstance(context)
        val parseResult = parse(context, repositoryName, uuid)

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

    private fun parse(context: Context, repositoryName: String, receipeUuid: String): ParseResult {
        val repoFolder = File(context.filesDir, repositoryName)
        val receipeFolder = File(repoFolder, "receipes")
        val receipeFile = File(receipeFolder, receipeUuid)
        val root = Parser().parse(FileReader(receipeFile)) as JsonObject

        // uuid
        val uuid = root.string("uuid")!!

        // nb_people
        val nbPeople = root.int("nb_people")!!

        // stars
        val stars = root.int("stars")!!

        // names
        val names: MutableMap<String, String> = mutableMapOf()
        for(jsonName in root.obj("names")!!.entries) {
            names[jsonName.key] = jsonName.value as String
        }

        // tags
        val tags: ArrayList<String> = ArrayList()
        for(jsonTag in root.array<String>("tags")!!) {
            tags.add(jsonTag)
        }

        // utensils
        val utensils: ArrayList<String> = ArrayList()
        for(jsonTag in root.array<String>("utensils")!!) {
            utensils.add(jsonTag)
        }

        // steps
        val steps: ArrayList<Step> = ArrayList()
        for(jsonStep in root.array<JsonObject>("steps")!!) {
            val stepUuid = jsonStep.string("step_uuid")!!
            val previousStepUuid = jsonStep.string("previous_step_uuid")!!
            val description = jsonStep.string("description")!!
            val duration = jsonStep.int("duration")!!

            // aliments
            val aliments: MutableMap<String, Int> = mutableMapOf()
            for(jsonAliment in root.obj("aliments")!!.entries) {
                aliments[jsonAliment.key] = jsonAliment.value as Int
            }

            // receipes
            val receipes: ArrayList<String> = ArrayList()
            for(jsonReceipe in jsonStep.array<String>("receipes")!!) {
                receipes.add(jsonReceipe)
            }


            // add to state array
            steps.add(Step(stepUuid, previousStepUuid, description, duration, aliments, receipes))
        }

        return ParseResult(uuid, names, nbPeople, stars, tags, utensils, steps)
    }
}