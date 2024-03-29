package com.realitix.nectar.database

import androidx.room.TypeConverter
import com.realitix.nectar.database.entity.GitSelectiveSynchronization
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.UpdateType

class Converter {
    // EntityType
    @TypeConverter
    fun entityTypeToOrdinal(v: EntityType) = v.ordinal
    @TypeConverter
    fun ordinalToEntityType(v: Int) = EntityType.values()[v]

    // UpdateType
    @TypeConverter
    fun updateTypeToOrdinal(v: UpdateType) = v.ordinal
    @TypeConverter
    fun ordinalToUpdateType(v: Int) = UpdateType.values()[v]

    // SynchronizationType
    @TypeConverter
    fun synchronizationTypeToOrdinal(v: GitSelectiveSynchronization.SynchronizationType) = v.ordinal
    @TypeConverter
    fun ordinalToSynchronizationType(v: Int) = GitSelectiveSynchronization.SynchronizationType.values()[v]
}