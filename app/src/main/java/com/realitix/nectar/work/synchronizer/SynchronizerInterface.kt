package com.realitix.nectar.work.synchronizer


interface SynchronizerInterface {
    fun fromGitToDb(gitRepositoryName: String, uuid: String)
}