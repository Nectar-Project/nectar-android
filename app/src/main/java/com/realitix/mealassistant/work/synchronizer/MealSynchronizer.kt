package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.beust.klaxon.Klaxon
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.MealRepository
import java.io.File

class MealSynchronizer: SynchronizerInterface {
    data class ParseResult(
        val uuid: String,
        val nbPeople: Int,
        val timestamp: Long,
        val description: String,
        val aliments: Map<String, Int>,
        val receipes: List<String>
    )

    override fun fromGitToDb(context: Context, repositoryName: String, uuid: String) {
        val repo = MealRepository.getInstance(context)
        val parseResult = parse(getFileContent(context, repositoryName, uuid))

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

    fun getFileContent(context: Context, repositoryName: String, uuid: String): String {
        val repoFolder = File(context.filesDir, repositoryName)
        val mealFolder = File(repoFolder, "meals")
        val mealFile = File(mealFolder, uuid)
        return mealFile.readText()
    }

    fun parse(json: String): ParseResult {
        return Klaxon().parse<ParseResult>(json)!!
    }
}