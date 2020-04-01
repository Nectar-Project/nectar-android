package com.realitix.nectar.work

import android.content.Context
import androidx.work.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GitManager
import com.realitix.nectar.util.MealUtil
import com.realitix.nectar.util.UuidGenerator
import com.realitix.nectar.work.synchronizer.*
import java.io.File

class GitRepositoryWorker(val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    private val synchronizerMap = mapOf(
        EntityType.STATE to StateSynchronizer(StateRepository(context), MealUtil.getRepositoryFolder(context)),
        EntityType.TAG to TagSynchronizer(TagRepository(context), MealUtil.getRepositoryFolder(context)),
        EntityType.MEASURE to MeasureSynchronizer(MeasureRepository(context), MealUtil.getRepositoryFolder(context)),
        EntityType.ALIMENT to AlimentSynchronizer(AlimentRepository(context), MealUtil.getRepositoryFolder(context), UuidGenerator()),
        EntityType.UTENSIL to UtensilSynchronizer(UtensilRepository(context), MealUtil.getRepositoryFolder(context)),
        EntityType.RECEIPE to ReceipeSynchronizer(ReceipeRepository(context), MealUtil.getRepositoryFolder(context)),
        EntityType.MEAL to MealSynchronizer(MealRepository(context), MealUtil.getRepositoryFolder(context)),
        EntityType.IMAGE to ImageSynchronizer(ImageRepository(context), MealUtil.getRepositoryFolder(context), MealUtil.getImageFolder(context))
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