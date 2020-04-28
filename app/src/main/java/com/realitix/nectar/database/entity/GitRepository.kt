package com.realitix.nectar.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.realitix.nectar.util.EntityType
import java.lang.Exception


class GitRepository(
    uuid: String, name: String, url: String, enabled: Boolean, rescan: Boolean, readOnly: Boolean, lastCheck: Long,
    frequency: Long, selectiveSynchronization: GitSelectiveSynchronization?, credentials: GitCredentials?
):
    GitRepositoryRaw(uuid, name, url, enabled, rescan, readOnly, lastCheck, frequency, selectiveSynchronization, credentials)

@Entity
open class GitRepositoryRaw(
    @PrimaryKey
    var uuid: String,
    var name: String,
    var url: String,
    var enabled: Boolean,
    // True to make a full rescan
    var rescan: Boolean,
    var readOnly: Boolean,
    // Seconds
    var lastCheck: Long,
    var frequency: Long,
    @Embedded(prefix="selective_")
    var selectiveSynchronization: GitSelectiveSynchronization?, // null means sync everything
    @Embedded(prefix="credentials_")
    var credentials: GitCredentials?
): UuidInterface {
    override fun getEntityUuid() = uuid
}

class GitCredentials(
    var username: String,
    var password: String
)

class GitSelectiveSynchronization(
    var synchronizationType: SynchronizationType,
    var level: EntityType? = null, // ALIMENT -> RECEIPE -> BOOK -> MEAL
    var bookUuid: String? = null
) {
    enum class SynchronizationType {
        SELECTIVE, BOOK
    }

    init {
        if(
            level != EntityType.ALIMENT &&
            level != EntityType.RECEIPE &&
            level != EntityType.BOOK &&
            level != EntityType.MEAL
        ) {
            throw Exception("selectiveLevel invalid")
        }
    }
}