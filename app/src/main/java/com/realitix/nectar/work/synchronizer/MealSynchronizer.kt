package com.realitix.nectar.work.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.MealRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class MealSynchronizer(repository: MealRepository, baseRepositoryFolder: File):
    BaseSynchronizer<MealSynchronizer.ParseResult, MealRepository>(repository, baseRepositoryFolder) {
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

    override fun populateParseResult(repo: MealRepository, uuid: String): ParseResult {
        val meal = repo.getMeal(uuid)!!

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