package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.beust.klaxon.Klaxon
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.util.EntityType
import java.io.File

class MealSynchronizer: BaseSynchronizer<MealSynchronizer.ParseResult, MealRepository>() {
    data class ParseResult(
        val uuid: String,
        val nbPeople: Int,
        val timestamp: Long,
        val description: String,
        val aliments: Map<String, Int>,
        val receipes: List<String>
    )

    override fun getEntityType(): EntityType = EntityType.MEAL
    override fun getParseResult(context: Context, repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(context, repositoryName, uuid)
    override fun getRepository(context: Context): MealRepository = MealRepository.getInstance(context)

    override fun updateDb(repo: MealRepository, parseResult: ParseResult) {
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
}