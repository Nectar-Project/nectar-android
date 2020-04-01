package com.realitix.nectar.work.synchronizer


interface SynchronizerInterface {
    fun fromGitToDb(gitRepositoryName: String, uuid: String)
    fun fromDbToGit(gitRepositoryName: String, uuid: String)
}