package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.MealAlimentRepository
import com.realitix.nectar.repository.MealReceipeRepository
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class MealSynchronizer(
    private val rMeal: MealRepository,
    private val rMealAliment: MealAlimentRepository,
    private val rMealReceipe: MealReceipeRepository,
    baseRepositoryFolder: File
): BaseSynchronizer<MealSynchronizer.ParseResult>(baseRepositoryFolder) {
    data class ParseResult(
        val uuid: String,
        val nbPeople: Int,
        val timestamp: Long,
        val description: String,
        val aliments: Map<String, Int>,
        val receipes: List<String>
    )

    override fun getEntityType(): EntityType = EntityType.MEAL
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)

    override fun updateDb(parseResult: ParseResult) {
        // Create meal only if not exists
        if(rMeal.get(parseResult.uuid) == null) {
            rMeal.insert(MealRaw(parseResult.uuid, parseResult.timestamp, parseResult.nbPeople, parseResult.description))
        }

        // aliments
        for ((alimentUuid, quantity) in parseResult.aliments) {
            rMealAliment.insert(MealAlimentRaw(alimentUuid, parseResult.uuid, quantity))
        }

        // receipes
        for (receipeUuid in parseResult.receipes) {
            rMealReceipe.insert(MealReceipeRaw(receipeUuid, parseResult.uuid))
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val meal = rMeal.get(uuid)!!

        val aliments = mutableMapOf<String, Int>()
        for(a in meal.aliments) {
            aliments[a.alimentUuid] = a.quantity
        }

        val receipes = mutableListOf<String>()
        for(r in meal.receipes) {
            receipes.add(r.receipeUuid)
        }

        return ParseResult(meal.uuid, meal.nbPeople, meal.timestamp, meal.description, aliments, receipes)
    }
}