package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Utensil(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}