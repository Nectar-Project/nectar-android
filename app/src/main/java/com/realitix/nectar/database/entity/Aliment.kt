package com.realitix.nectar.database.entity


import androidx.room.*


class Aliment(uuid: String, nameUuid: String): AlimentRaw(uuid, nameUuid) {
    //@Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentNameRaw::class)
    //lateinit var names: List<AlimentName>
    @Relation(parentColumn = "nameUuid", entityColumn = "uuid", entity = StringKeyRaw::class)
    lateinit var name: StringKey
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentTagRaw::class)
    lateinit var tags: List<AlimentTag>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentStateRaw::class)
    lateinit var states: List<AlimentState>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentImageRaw::class)
    lateinit var images: List<AlimentImage>

    fun getName(): String {
        return name.strings[0].value
        //return names[0].name
    }
}

@Entity
open class AlimentRaw (
    @PrimaryKey
    var uuid: String,
    var nameUuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentRaw

        if (uuid != other.uuid) return false
        if (nameUuid != other.nameUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nameUuid.hashCode()
        return result
    }
}




