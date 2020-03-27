package com.realitix.mealassistant.work.parser

import android.content.Context
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import java.io.File
import java.io.FileReader

class MealParser: ParserInterface {
    class ParseResult(
        val uuid: String,
        val nbPeople: Int,
        val timestamp: Long,
        val description: String,
        val aliments: Map<String, Int>,
        val receipes: List<String>
    )

    override fun update(context: Context, repositoryName: String, uuid: String) {
        val repo = MealRepository.getInstance(context)
        val parseResult = parse(context, repositoryName, uuid)

        // Create meal only if not exists
        if(repo.getMeal(parseResult.uuid) == null) {
            repo.insertMeal(MealRaw(parseResult.uuid, parseResult.timestamp, parseResult.nbPeople, parseResult.description))
        }

        // aliments
        for ((alimentUuid, quantity) in parseResult.aliments) {
            repo.insertMealAliment(MealAlimentRaw(alimentUuid, parseResult.uuid, quantity))
        }

        // receipes
        for (receipeUuid in parseResult.receipes) {
            repo.insertMealReceipe(MealReceipeRaw(receipeUuid, parseResult.uuid))
        }
    }

    private fun parse(context: Context, repositoryName: String, mealUuid: String): ParseResult {
        val repoFolder = File(context.filesDir, repositoryName)
        val mealFolder = File(repoFolder, "meals")
        val mealFile = File(mealFolder, mealUuid)
        val root = Parser().parse(FileReader(mealFile)) as JsonObject

        val uuid = root.string("uuid")!!
        val nbPeople = root.int("nb_people")!!
        val timestamp = root.long("timestamp")!!
        val description = root.string("description")!!

        // aliments
        val aliments: MutableMap<String, Int> = mutableMapOf()
        for(jsonAliment in root.obj("aliments")!!.entries) {
            aliments[jsonAliment.key] = jsonAliment.value as Int
        }

        // receipes
        val receipes: ArrayList<String> = ArrayList()
        for(jsonReceipe in root.array<String>("receipes")!!) {
            receipes.add(jsonReceipe)
        }

        return ParseResult(uuid, nbPeople, timestamp, description, aliments, receipes)
    }
}