package com.realitix.mealassistant.database.entity

import androidx.room.*


class AlimentImage(alimentUuid: String, imageUuid: String): AlimentImageRaw(alimentUuid, imageUuid)

@Entity(
    primaryKeys = ["alimentUuid", "imageUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ImageRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["imageUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"]),
        Index(value=["imageUuid"])
    ]
)
open class AlimentImageRaw (
    var alimentUuid: String,
    var imageUuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentImageRaw

        if (alimentUuid != other.alimentUuid) return false
        if (imageUuid != other.imageUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentUuid.hashCode()
        result = 31 * result + imageUuid.hashCode()
        return result
    }

}
