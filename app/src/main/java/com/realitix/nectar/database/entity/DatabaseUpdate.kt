package com.realitix.nectar.database.entity

import androidx.room.Entity
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.UpdateType


class DatabaseUpdate(entityUuid: String, entityType: EntityType, updateType: UpdateType, timestamp: Long):
    DatabaseUpdateRaw(entityUuid, entityType, updateType, timestamp)

@Entity(primaryKeys = ["entityUuid", "entityType", "updateType"])
open class DatabaseUpdateRaw(
    var entityUuid: String,
    var entityType: EntityType,
    var updateType: UpdateType,
    var timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DatabaseUpdateRaw

        if (entityUuid != other.entityUuid) return false
        if (entityType != other.entityType) return false
        if (timestamp != other.timestamp) return false
        if (updateType != other.updateType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = entityUuid.hashCode()
        result = 31 * result + entityType.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + updateType.hashCode()
        return result
    }

}