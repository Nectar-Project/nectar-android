package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


class AlimentCategory(name: String) : AlimentCategoryRaw(name)

@Entity(indices = [Index(value = ["name"], unique = true)])
open class AlimentCategoryRaw(var name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}