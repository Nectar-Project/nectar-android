package com.realitix.nectar.background

import android.content.Context
import android.util.Log
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.GitManager
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UuidGenerator
import com.realitix.nectar.background.synchronizer.*
import com.realitix.nectar.database.entity.GitRepository
import java.io.File
import org.eclipse.jgit.api.errors.TransportException

class GitRepositorySynchronizer(val context: Context) {

    private val synchronizerMap = mapOf(
        EntityType.STATE to StateSynchronizer(
            StateRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.TAG to TagSynchronizer(
            TagRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.MEASURE to MeasureSynchronizer(
            MeasureRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.ALIMENT to AlimentSynchronizer(
            AlimentRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            AlimentImageRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            AlimentTagRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            AlimentStateRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            AlimentStateMeasureRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context), UuidGenerator()
        ),
        EntityType.UTENSIL to UtensilSynchronizer(
            UtensilRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.RECEIPE to ReceipeSynchronizer(
            ReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ReceipeMeasureRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ReceipeTagRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ReceipeUtensilRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ReceipeStepRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ReceipeStepAlimentRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ReceipeStepReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.MEAL to MealSynchronizer(
            MealRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            MealAlimentRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            MealReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.IMAGE to ImageSynchronizer(
            ImageRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context),
            NectarUtil.getImageFolder(context)
        ),
        EntityType.BOOK to BookSynchronizer(
            BookRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            BookImageRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            BookReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.STRING_KEY to StringKeySynchronizer(
            StringKeyRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            StringKeyValueRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        )
    )

    private fun fromGitToDbRepository(gitRepositoryName: String, diff: GitManager.DiffResult): Boolean {
        val sortedUpdates = diff.updates.sortedWith(compareBy{it.first.ordinal})
        for((dt, uuid) in sortedUpdates) {
            try {
                synchronizerMap[dt]?.fromGitToDb(gitRepositoryName, uuid)
            }
            catch(e: Exception) {
                Log.e("nectar", "fromGitToDb error in repo $gitRepositoryName: ${dt.folderName}/$uuid can't be parsed. Error: $e")
            }
        }

        return sortedUpdates.isNotEmpty()
    }

    @Synchronized
    private fun synchronizeFromGitToDb(
        gitRepository: GitRepository,
        currentTimestamp: Long,
        baseRepositoryFolder: File
    ) {
        val manager = GitManager(
            File(baseRepositoryFolder, gitRepository.name),
            gitRepository.url,
            gitRepository.credentials
        )

        var updated = false

        if(gitRepository.rescan) {
            updated = fromGitToDbRepository(gitRepository.name, manager.rescan())
        }
        else if ((currentTimestamp - gitRepository.lastCheck >= gitRepository.frequency) && manager.fetch()) {
            updated = fromGitToDbRepository(gitRepository.name, manager.diff())
            manager.sync()
        }

        if(updated) {
            NectarUtil.showNotification(context, "Repository ${gitRepository.name} updated")
        }
    }

    @Synchronized
    private fun synchronizeFromDbToGit(
       gitRepository: GitRepository,
       baseRepositoryFolder: File
    ) {
        if(gitRepository.readOnly)
            return

        val manager = GitManager(
            File(baseRepositoryFolder, gitRepository.name),
            gitRepository.url,
            gitRepository.credentials
        )

        // Sync server to local before local synchronization
        if(manager.fetch()) {
            manager.sync()
        }

        val rDatabaseUpdate = DatabaseUpdateRepository(context)
        val updates = if(gitRepository.rescan) rDatabaseUpdate.rescan() else rDatabaseUpdate.list()

        var updated = false
        for(u in updates) {
            synchronizerMap[u.entityType]?.fromDbToGit(gitRepository.name, u.entityUuid)
            updated = true
        }

        if(updated) {
            // Check diff with origin
            val diff = manager.diff()
            if(diff.updates.isNotEmpty() || diff.deletes.isNotEmpty()) {
                Log.i("Nectar", "Push updates to ${gitRepository.name}")
                manager.addAll()
                manager.commit()
                manager.push()
            }
        }

        // Clean updates
        for(u in updates) {
            rDatabaseUpdate.delete(u)
        }
    }

    @Synchronized
    fun exec(gitRepositoryUuid: String? = null) {
        val currentTimestamp: Long = System.currentTimeMillis() / 1000
        val baseRepositoryFolder = NectarUtil.getRepositoryFolder(context)
        val rGitRepository = GitRepoRepository(context)
        val gitRepositories = if(gitRepositoryUuid == null) {
            rGitRepository.listEnabled()
        } else {
            val g = rGitRepository.get(gitRepositoryUuid)!!
            Log.i("Nectar", "Force sync of repository ${g.name}")
            listOf(g)
        }

        for(gitRepository in gitRepositories) {
            try {
                synchronizeFromGitToDb(gitRepository, currentTimestamp, baseRepositoryFolder)
                synchronizeFromDbToGit(gitRepository, baseRepositoryFolder)
            } catch (e: TransportException) {
                continue
            }

            gitRepository.lastCheck = currentTimestamp
            gitRepository.rescan = false
            rGitRepository.update(gitRepository)
        }
    }
}