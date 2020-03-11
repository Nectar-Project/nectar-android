package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Meal(timestamp: Long, nb_people: Int, description: String): MealRaw(timestamp, nb_people, description) {
    @Relation(parentColumn = "id", entityColumn = "mealId", entity = MealAlimentRaw::class)
    var aliments: List<MealAliment>? = null
    @Relation(parentColumn = "id", entityColumn = "mealId", entity = MealReceipeRaw::class)
    var receipes: List<MealReceipe>? = null
}

@Entity
open class MealRaw(var timestamp: Long, var nb_people: Int, var description: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}