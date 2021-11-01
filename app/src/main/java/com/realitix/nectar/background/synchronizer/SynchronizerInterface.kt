package com.realitix.nectar.background.synchronizer


interface SynchronizerInterface {
    fun fromGitToDb(gitRepositoryName: String, uuid: String)
    fun fromDbToGit(gitRepositoryName: String, uuid: String, forceDelete: Boolean)
    fun fromGitDeleteInDb(uuid: String)
}