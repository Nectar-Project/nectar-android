package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = AlimentNameRaw::class)
@Entity
class AlimentNameFts(var name: String)