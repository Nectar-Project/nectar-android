package com.realitix.mealassistant.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.realitix.mealassistant.repository.GitRepoRepository
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.GitManager
import com.realitix.mealassistant.work.synchronizer.*
import java.io.File

class GitRepositoryWorker(val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    private val parserMap = mapOf(
        EntityType.STATE to StateSynchronizer(),
        EntityType.TAG to TagSynchronizer(),
        EntityType.MEASURE to MeasureSynchronizer(),
        EntityType.ALIMENT to AlimentSynchronizer(),
        EntityType.UTENSIL to UtensilSynchronizer(),
        EntityType.RECEIPE to ReceipeSynchronizer(),
        EntityType.MEAL to MealSynchronizer()
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
                            parserMap[dt]?.fromGitToDb(context, repo.name, uuid)
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