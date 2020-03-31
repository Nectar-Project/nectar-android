package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.FtsOptions

@Fts4(
    contentEntity = AlimentNameRaw::class,
    tokenizer = FtsOptions.TOKENIZER_UNICODE61
)
@Entity
class AlimentNameFts(var name: String)