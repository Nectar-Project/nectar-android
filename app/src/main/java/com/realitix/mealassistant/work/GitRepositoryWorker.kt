package com.realitix.mealassistant.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.realitix.mealassistant.repository.*
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.GitManager
import com.realitix.mealassistant.work.synchronizer.*
import java.io.File

class GitRepositoryWorker(val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    val synchronizerMap = mapOf(
        EntityType.STATE to StateSynchronizer(context, StateRepository(context)),
        EntityType.TAG to TagSynchronizer(context, TagRepository(context)),
        EntityType.MEASURE to MeasureSynchronizer(context, MeasureRepository(context)),
        EntityType.ALIMENT to AlimentSynchronizer(context, AlimentRepository(context)),
        EntityType.UTENSIL to UtensilSynchronizer(context, UtensilRepository(context)),
        EntityType.RECEIPE to ReceipeSynchronizer(context, ReceipeRepository(context)),
        EntityType.MEAL to MealSynchronizer(context, MealRepository(context))
    )

    override fun doWork(): Result {
        val repos = GitRepoRepository(context).listGitRepositories()
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
                            synchronizerMap[dt]?.fromGitToDb(repo.name, uuid)
                        }
                    }
                    manager.sync()
                }

                repo.lastCheck = currentTimestamp
                GitRepoRepository(context).updateGitRepository(repo)
            }
        }

        return Result.success()
    }
}