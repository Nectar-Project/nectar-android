package com.realitix.nectar.database.entity

import androidx.room.*


class ReceipeImage(receipeUuid: String, imageUuid: String): ReceipeImageRaw(receipeUuid, imageUuid)

@Entity(
    primaryKeys = ["receipeUuid", "imageUuid"],
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ImageRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["imageUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["imageUuid"])
    ]
)
open class ReceipeImageRaw (
    var receipeUuid: String,
    var imageUuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeImageRaw

        if (receipeUuid != other.receipeUuid) return false
        if (imageUuid != other.imageUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = receipeUuid.hashCode()
        result = 31 * result + imageUuid.hashCode()
        return result
    }
}
