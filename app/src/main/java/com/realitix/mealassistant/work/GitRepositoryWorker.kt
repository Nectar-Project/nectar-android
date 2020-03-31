package com.realitix.mealassistant.work

import android.content.Context
import android.util.Log
import androidx.work.*
import com.realitix.mealassistant.repository.*
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.GitManager
import com.realitix.mealassistant.util.MealUtil
import com.realitix.mealassistant.util.UuidGenerator
import com.realitix.mealassistant.work.synchronizer.*
import java.io.File
import java.util.concurrent.TimeUnit

class GitRepositoryWorker(val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    val synchronizerMap = mapOf(
        EntityType.STATE to StateSynchronizer(context, StateRepository(context)),
        EntityType.TAG to TagSynchronizer(context, TagRepository(context)),
        EntityType.MEASURE to MeasureSynchronizer(context, MeasureRepository(context)),
        EntityType.ALIMENT to AlimentSynchronizer(context, AlimentRepository(context), UuidGenerator()),
        EntityType.UTENSIL to UtensilSynchronizer(context, UtensilRepository(context)),
        EntityType.RECEIPE to ReceipeSynchronizer(context, ReceipeRepository(context)),
        EntityType.MEAL to MealSynchronizer(context, MealRepository(context)),
        EntityType.IMAGE to ImageSynchronizer(context, ImageRepository(context))
    )

    private fun synchronize(repositoryName: String, diff: GitManager.DiffResult) {
        val sortedUpdates = diff.updates.sortedWith(compareBy{it.first.ordinal})
        for((dt, uuid) in sortedUpdates) {
            synchronizerMap[dt]?.fromGitToDb(repositoryName, uuid)
        }
    }

    override fun doWork(): Result {
        val repos = GitRepoRepository(context).listGitRepositories()
        val rName = MealUtil.getProperty(context, "repositoryNameFolder")
        val baseRepository = File(context.filesDir, rName)

        for(repo in repos) {
            val currentTimestamp: Long = System.currentTimeMillis() / 1000
            if(currentTimestamp - repo.lastCheck >= repo.frequency) {

                val manager = GitManager(
                    File(baseRepository, repo.name),
                    repo.url,
                    repo.credentials
                )

                if(repo.rescan) {
                    synchronize(repo.name, manager.rescan())
                    repo.rescan = false
                }
                else if (manager.fetch()) {
                    synchronize(repo.name, manager.diff())
                    manager.sync()
                }


                repo.lastCheck = currentTimestamp
                GitRepoRepository(context).updateGitRepository(repo)
            }
        }

        return Result.success()
    }
}