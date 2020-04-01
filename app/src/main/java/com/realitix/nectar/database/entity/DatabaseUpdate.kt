package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.realitix.nectar.util.EntityType


class DatabaseUpdate(entityUuid: String, entityType: EntityType, timestamp: Long):
    DatabaseUpdateRaw(entityUuid, entityType, timestamp)

@Entity(primaryKeys = ["entityUuid", "entityType"])
open class DatabaseUpdateRaw(
    var entityUuid: String,
    var entityType: EntityType,
    var timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DatabaseUpdateRaw

        if (entityUuid != other.entityUuid) return false
        if (entityType != other.entityType) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entityUuid.hashCode()
        result = 31 * result + entityType.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }
}