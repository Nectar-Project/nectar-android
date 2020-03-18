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
)