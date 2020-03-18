package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Meal(uuid: String, timestamp: Long, nb_people: Int, description: String): MealRaw(uuid, timestamp, nb_people, description) {
    @Relation(parentColumn = "uuid", entityColumn = "mealUuid", entity = MealAlimentRaw::class)
    var aliments: List<MealAliment>? = null
    @Relation(parentColumn = "uuid", entityColumn = "mealUuid", entity = MealReceipeRaw::class)
    var receipes: List<MealReceipe>? = null
}

@Entity
open class MealRaw(
    @PrimaryKey
    var uuid: String,
    var timestamp: Long,
    var nb_people: Int,
    var description: String
)