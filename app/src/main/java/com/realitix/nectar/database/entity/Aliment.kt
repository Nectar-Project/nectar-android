package com.realitix.nectar.database.entity


import androidx.room.*


class Aliment(uuid: String): AlimentRaw(uuid) {
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentNameRaw::class)
    lateinit var names: List<AlimentName>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentTagRaw::class)
    lateinit var tags: List<AlimentTag>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentStateRaw::class)
    lateinit var states: List<AlimentState>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentImageRaw::class)
    lateinit var images: List<AlimentImage>

    fun getName(): String {
        return names[0].name
    }
}

@Entity
open class AlimentRaw (
    @PrimaryKey
    var uuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentRaw

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}




