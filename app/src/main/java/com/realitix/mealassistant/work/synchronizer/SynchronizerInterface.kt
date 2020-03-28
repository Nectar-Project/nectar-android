package com.realitix.mealassistant.work.synchronizer

import android.content.Context

interface SynchronizerInterface {
    fun fromGitToDb(context: Context, repositoryName: String, uuid: String)
}