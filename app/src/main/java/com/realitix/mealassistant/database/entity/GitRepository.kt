package com.realitix.mealassistant.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


class GitRepository(uuid: String, name: String, url: String, rescan: Boolean, readOnly: Boolean, lastCheck: Long, frequency: Long, credentials: GitCredentials?):
    GitRepositoryRaw(uuid, name, url, rescan, readOnly, lastCheck, frequency, credentials)

@Entity
open class GitRepositoryRaw(
    @PrimaryKey
    var uuid: String,
    var name: String,
    var url: String,
    // True to make a full rescan
    var rescan: Boolean,
    var readOnly: Boolean,
    // Seconds
    var lastCheck: Long,
    var frequency: Long,
    @Embedded(prefix="credentials_")
    var credentials: GitCredentials?
)

class GitCredentials(
    var username: String,
    var password: String
)