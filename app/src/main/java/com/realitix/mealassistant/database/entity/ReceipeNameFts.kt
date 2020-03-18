package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = ReceipeNameRaw::class)
@Entity
class ReceipeNameFts(var name: String)