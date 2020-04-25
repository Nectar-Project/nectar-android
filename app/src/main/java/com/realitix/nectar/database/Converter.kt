package com.realitix.nectar.database

import androidx.room.TypeConverter
import com.realitix.nectar.util.EntityType

class Converter {
    // EntityType
    @TypeConverter
    fun entityTypeToOrdinal(v: EntityType) = v.ordinal
    @TypeConverter
    fun ordinalToEntityType(v: Int) = EntityType.values()[v]
}