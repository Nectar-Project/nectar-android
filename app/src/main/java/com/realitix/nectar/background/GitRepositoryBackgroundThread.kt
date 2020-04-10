package com.realitix.nectar.background

import android.content.Context
import android.util.Log
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GitManager
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UuidGenerator
import com.realitix.nectar.background.synchronizer.*
import java.io.File

class GitRepositoryBackgroundThread(context: Context): Thread() {
    private var run = true

    private val synchronizer = GitRepositorySynchronizer(context)

    fun stopOnNextIteration() {
        run = false
    }

    override fun run() {
        val millis: Long = 1000*60
        while(run) {
            synchronizer.exec()
            sleep(millis)
        }
    }
}