package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


class Image(uuid: String, path: String): ImageRaw(uuid, path)

@Entity
open class ImageRaw (
    @PrimaryKey
    var uuid: String,
    var path: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageRaw

        if (uuid != other.uuid) return false
        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }
}