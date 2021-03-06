package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.FtsOptions

@Fts4(
    contentEntity = StringKeyValueRaw::class,
    tokenizer = FtsOptions.TOKENIZER_UNICODE61
)
@Entity
class StringKeyValueFts(var value: String)