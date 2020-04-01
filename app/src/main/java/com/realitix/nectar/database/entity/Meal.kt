package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Meal(uuid: String, timestamp: Long, nb_people: Int, description: String): MealRaw(uuid, timestamp, nb_people, description) {
    @Relation(parentColumn = "uuid", entityColumn = "mealUuid", entity = MealAlimentRaw::class)
    lateinit var aliments: List<MealAliment>
    @Relation(parentColumn = "uuid", entityColumn = "mealUuid", entity = MealReceipeRaw::class)
    lateinit var receipes: List<MealReceipe>
}

@Entity
open class MealRaw(
    @PrimaryKey
    var uuid: String,
    var timestamp: Long,
    var nb_people: Int,
    var description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealRaw

        if (uuid != other.uuid) return false
        if (timestamp != other.timestamp) return false
        if (nb_people != other.nb_people) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + nb_people
        result = 31 * result + description.hashCode()
        return result
    }
}