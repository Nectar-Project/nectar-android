package com.realitix.nectar.work

import android.content.Context
import androidx.work.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GitManager
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UuidGenerator
import com.realitix.nectar.work.synchronizer.*
import java.io.File

class GitRepositoryWorker(val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    private val synchronizerMap = mapOf(
        EntityType.STATE to StateSynchronizer(StateRepository(context), NectarUtil.getRepositoryFolder(context)),
        EntityType.TAG to TagSynchronizer(TagRepository(context), NectarUtil.getRepositoryFolder(context)),
        EntityType.MEASURE to MeasureSynchronizer(MeasureRepository(context), NectarUtil.getRepositoryFolder(context)),
        EntityType.ALIMENT to AlimentSynchronizer(AlimentRepository(context), NectarUtil.getRepositoryFolder(context), UuidGenerator()),
        EntityType.UTENSIL to UtensilSynchronizer(UtensilRepository(context), NectarUtil.getRepositoryFolder(context)),
        EntityType.RECEIPE to ReceipeSynchronizer(ReceipeRepository(context), NectarUtil.getRepositoryFolder(context)),
        EntityType.MEAL to MealSynchronizer(MealRepository(context), NectarUtil.getRepositoryFolder(context)),
        EntityType.IMAGE to ImageSynchronizer(ImageRepository(context), NectarUtil.getRepositoryFolder(context), NectarUtil.getImageFolder(context)),
        EntityType.BOOK to BookSynchronizer(BookRepository(context), NectarUtil.getRepositoryFolder(context))
    )

    private fun fromGitToDbRepository(gitRepositoryName: String, diff: GitManager.DiffResult) {
        val sortedUpdates = diff.updates.sortedWith(compareBy{it.first.ordinal})
        for((dt, uuid) in sortedUpdates) {
            synchronizerMap[dt]?.fromGitToDb(gitRepositoryName, uuid)
        }
    }

    private fun synchronizeFromGitToDb() {
        val repos = GitRepoRepository(context).listGitRepositories()
        val baseRepositoryFolder = NectarUtil.getRepositoryFolder(context)

        for(repo in repos) {
            val currentTimestamp: Long = System.currentTimeMillis() / 1000
            if(currentTimestamp - repo.lastCheck >= repo.frequency) {
                val manager = GitManager(
                    File(baseRepositoryFolder, repo.name),
                    repo.url,
                    repo.credentials
                )

                if(repo.rescan) {
                    fromGitToDbRepository(repo.name, manager.rescan())
                    repo.rescan = false
                }
                else if (manager.fetch()) {
                    fromGitToDbRepository(repo.name, manager.diff())
                    manager.sync()
                }

                repo.lastCheck = currentTimestamp
                GitRepoRepository(context).updateGitRepository(repo)
            }
        }
    }

    private fun synchronizeFromDbToGit() {
        val baseRepositoryFolder = NectarUtil.getRepositoryFolder(context)
        val gitRepositories = GitRepoRepository(context).listGitRepositories()
        val updates = DatabaseUpdateRepository(context).list()

        for(g in gitRepositories) {
            if(g.readOnly)
                continue

            var updated = false
            for(u in updates) {
                synchronizerMap[u.entityType]?.fromDbToGit(g.name, u.entityUuid)
                updated = true
            }

            if(updated) {
                val manager = GitManager(
                    File(baseRepositoryFolder, g.name),
                    g.url,
                    g.credentials
                )
                manager.addAll()
                manager.commit()
                manager.push()
            }
        }
    }

    override fun doWork(): Result {
        synchronizeFromGitToDb()
        synchronizeFromDbToGit()

        return Result.success()
    }
}