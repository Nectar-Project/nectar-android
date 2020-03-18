package com.realitix.mealassistant.database.entity


import androidx.room.*


class Aliment(uuid: String): AlimentRaw(uuid) {
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentNameRaw::class)
    var names: List<AlimentName>? = null
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentTagRaw::class)
    var tags: List<AlimentTag>? = null

    fun getName(): String {
        return names!![0].name
    }
}

@Entity
open class AlimentRaw (
    @PrimaryKey
    var uuid: String
)




