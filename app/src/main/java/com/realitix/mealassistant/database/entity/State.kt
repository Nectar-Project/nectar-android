package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


class State(uuid: String): StateRaw(uuid)

@Entity
open class StateRaw (
    @PrimaryKey
    var uuid: String
)