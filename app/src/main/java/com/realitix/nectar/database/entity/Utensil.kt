package com.realitix.nectar.database.entity

import androidx.room.*


class Utensil(uuid: String, nameUuid: String): UtensilRaw(uuid, nameUuid) {
    @Relation(parentColumn = "nameUuid", entityColumn = "uuid", entity = StringKeyRaw::class)
    lateinit var name: StringKey

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
open class UtensilRaw(
    @PrimaryKey
    var uuid: String,
    var nameUuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UtensilRaw

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