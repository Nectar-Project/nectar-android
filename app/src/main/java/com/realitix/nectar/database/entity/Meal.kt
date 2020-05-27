package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


class Meal(uuid: String, timestamp: Long, nbPeople: Int, description: String): MealRaw(uuid, timestamp, nbPeople, description) {
    @Relation(parentColumn = "uuid", entityColumn = "mealUuid", entity = MealAlimentStateRaw::class)
    lateinit var aliments: List<MealAlimentState>
    @Relation(parentColumn = "uuid", entityColumn = "mealUuid", entity = MealReceipeRaw::class)
    lateinit var receipes: List<MealReceipe>
}

@Entity
open class MealRaw(
    @PrimaryKey
    var uuid: String,
    var timestamp: Long,
    var nbPeople: Int,
    var description: String
): UuidInterface {
    override fun getEntityUuid() = uuid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MealRaw

        if (uuid != other.uuid) return false
        if (timestamp != other.timestamp) return false
        if (nbPeople != other.nbPeople) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + nbPeople
        result = 31 * result + description.hashCode()
        return result
    }
}