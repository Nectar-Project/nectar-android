package com.realitix.nectar.database.entity

import androidx.room.PrimaryKey

open class UuidEntity(
    @PrimaryKey
    var uuid: String
)