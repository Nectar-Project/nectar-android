package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

class Tag(uuid: String): TagRaw(uuid)

@Entity
open class TagRaw (
    @PrimaryKey
    var uuid: String
)