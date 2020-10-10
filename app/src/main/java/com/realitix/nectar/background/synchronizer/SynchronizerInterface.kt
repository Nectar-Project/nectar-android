package com.realitix.nectar.background.synchronizer


interface SynchronizerInterface {
    fun fromGitToDb(gitRepositoryName: String, uuid: String)
    fun fromDbToGit(gitRepositoryName: String, uuid: String)
    fun fromGitDeleteInDb(uuid: String)
}