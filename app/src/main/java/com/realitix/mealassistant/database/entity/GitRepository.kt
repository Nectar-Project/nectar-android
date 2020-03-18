package com.realitix.mealassistant.database.entity

import androidx.room.Embedded
import androidx.room.Entity


class GitRepository(uuid: String, name: String, url: String, readOnly: Boolean, credentials: GitCredentials?):
    GitRepositoryRaw(uuid, name, url, readOnly, credentials)

@Entity
open class GitRepositoryRaw(
    var uuid: String,
    var name: String,
    var url: String,
    var readOnly: Boolean,
    @Embedded
    var credentials: GitCredentials?
)

class GitCredentials(
    var username: String,
    var password: String
)