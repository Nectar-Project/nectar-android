package com.realitix.nectar.database.entity


import androidx.room.*


class Aliment(uuid: String, nameUuid: String): AlimentWS(uuid, nameUuid) {
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentStateRaw::class)
    lateinit var states: List<AlimentState>
}

open class AlimentWS(uuid: String, nameUuid: String): AlimentRaw(uuid, nameUuid) {
    @Relation(parentColumn = "nameUuid", entityColumn = "uuid", entity = StringKeyRaw::class)
    lateinit var name: StringKey
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentTagRaw::class)
    lateinit var tags: List<AlimentTag>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentImageRaw::class)
    lateinit var images: List<AlimentImage>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentPriceRaw::class)
    lateinit var price: AlimentPrice

    fun getName(): String {
        return name.getValue()
    }
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = StringKeyRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["nameUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["nameUuid"])
    ]
)
open class AlimentRaw (
    @PrimaryKey
    var uuid: String,
    var nameUuid: String
): UuidInterface {
    override fun getEntityUuid() = uuid

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