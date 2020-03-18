package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


class Language(uuid: String, code: String): LanguageRaw(uuid, code)

@Entity
open class LanguageRaw (
    @PrimaryKey
    var uuid: String,
    var code: String
)