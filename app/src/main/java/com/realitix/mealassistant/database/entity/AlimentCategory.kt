package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = ["name"], unique = true)))
class AlimentCategory(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}