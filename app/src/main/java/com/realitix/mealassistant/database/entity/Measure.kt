package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


class Measure(uuid: String): MeasureRaw(uuid)

@Entity
open class MeasureRaw (
    @PrimaryKey
    var uuid: String
)