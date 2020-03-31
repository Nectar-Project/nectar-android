package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

class Tag(uuid: String): TagRaw(uuid) {
    @Relation(parentColumn = "uuid", entityColumn = "tagUuid", entity = TagNameRaw::class)
    var names: List<TagName>? = null
}

@Entity
open class TagRaw (
    @PrimaryKey
    var uuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TagRaw

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}