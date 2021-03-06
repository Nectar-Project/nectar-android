package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.background.DummyNotifier
import com.realitix.nectar.background.NotifierInterface
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
    baseRepositoryFolder: File,
    private val notifier: NotifierInterface = DummyNotifier()
): BaseSynchronizer<MealSynchronizer.ParseResult>(baseRepositoryFolder) {
    data class ParseResult(
        val uuid: String,
        val nbPeople: Int,
        val timestamp: Long,
        val description: String,
        val aliments: Map<String, Int>,
        val receipes: Map<String, Float>
    )

    override fun getEntityType(): EntityType = EntityType.MEAL
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)
    override fun isEntityExists(uuid: String): Boolean = rMeal.get(uuid) != null

    override fun fromGitDeleteInDb(uuid: String) {
        val meal = rMeal.get(uuid)!!

        for(aliment in meal.aliments) {
            rMealAliment.delete(aliment)
        }

        for(receipe in meal.receipes) {
            rMealReceipe.delete(receipe)
        }

        rMeal.delete(meal)
    }

    override fun updateDb(parseResult: ParseResult) {
        // Create meal only if not exists
        if(rMeal.get(parseResult.uuid) == null) {
            rMeal.insert(MealRaw(parseResult.uuid, parseResult.timestamp, parseResult.nbPeople, parseResult.description))
        }

        // aliments
        for ((alimentUuid, weight) in parseResult.aliments) {
            if(rMealAliment.get(parseResult.uuid, alimentUuid) == null) {
                rMealAliment.insert(MealAlimentStateRaw(parseResult.uuid, alimentUuid, weight))
            }
        }

        // receipes
        for ((receipeUuid, proportion) in parseResult.receipes) {
            if(rMealReceipe.get(parseResult.uuid, receipeUuid) == null) {
                rMealReceipe.insert(MealReceipeRaw(parseResult.uuid, receipeUuid, proportion))
            }
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val meal = rMeal.get(uuid)!!

        val aliments = mutableMapOf<String, Int>()
        for(a in meal.aliments) {
            aliments[a.alimentStateUuid] = a.weight
        }

        val receipes = mutableMapOf<String, Float>()
        for(r in meal.receipes) {
            receipes[r.receipeUuid] = r.proportion
        }

        return ParseResult(meal.uuid, meal.nbPeople, meal.timestamp, meal.description, aliments, receipes)
    }
}