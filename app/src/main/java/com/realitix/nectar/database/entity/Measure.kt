package com.realitix.nectar.database.entity

import androidx.room.*


class Measure(uuid: String, nameUuid: String): MeasureRaw(uuid, nameUuid) {
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
open class MeasureRaw (
    @PrimaryKey
    var uuid: String,
    var nameUuid: String
): UuidInterface {
    override fun getEntityUuid() = uuid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MeasureRaw

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