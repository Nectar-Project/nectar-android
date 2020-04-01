package com.realitix.nectar.database

import androidx.room.TypeConverter
import com.realitix.nectar.util.EntityType

class Converter {
    @TypeConverter
    fun entityTypeToOrdinal(v: EntityType): Int = v.ordinal

    @TypeConverter
    fun ordinalToEntityType(v: Int): EntityType = EntityType.values()[v]
}