package com.realitix.mealassistant.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.realitix.mealassistant.repository.GitRepoRepository
import com.realitix.mealassistant.repository.UtensilRepository
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.GitManager
import com.realitix.mealassistant.work.parser.*
import java.io.File

class GitRepositoryWorker(val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    private val parserMap = mapOf(
        EntityType.STATE to StateParser(),
        EntityType.TAG to TagParser(),
        EntityType.MEASURE to MeasureParser(),
        EntityType.ALIMENT to AlimentParser(),
        EntityType.UTENSIL to UtensilParser()
    )

    override fun doWork(): Result {
        val repos = GitRepoRepository.getInstance(context).listGitRepositories()
        for(repo in repos) {
            val currentTimestamp: Long = System.currentTimeMillis() / 1000
            if(currentTimestamp - repo.lastCheck >= repo.frequency) {
                val manager = GitManager(
                    File(context.filesDir, repo.name),
                    repo.url,
                    repo.credentials
                )
                if (manager.fetch()) {
                    val diff = manager.diff()
                    if(diff.hasResult) {
                        // Sort the map by priority order
                        val sortedUpdates = diff.updates.sortedWith(compareBy{it.first.ordinal})
                        for((dt, uuid) in sortedUpdates) {
                            parserMap[dt]?.update(context, repo.name, uuid)
                        }
                    }
                    manager.sync()
                }

                repo.lastCheck = currentTimestamp
                GitRepoRepository.getInstance(context).updateGitRepository(repo)
            }
        }

        return Result.success()
    }
}