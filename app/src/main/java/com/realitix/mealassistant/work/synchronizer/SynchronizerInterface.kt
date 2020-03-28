package com.realitix.mealassistant.work.synchronizer


interface SynchronizerInterface {
    fun fromGitToDb(gitRepositoryName: String, uuid: String)
}