package com.realitix.nectar.background

import android.content.Context
import android.util.Log
import com.realitix.nectar.repository.*
import com.realitix.nectar.background.synchronizer.*
import com.realitix.nectar.database.entity.GitRepository
import com.realitix.nectar.database.entity.GitSelectiveSynchronization
import com.realitix.nectar.util.*
import java.io.File
import org.eclipse.jgit.api.errors.TransportException

class GitRepositorySynchronizer(val context: Context) {

    private val notifier = DefaultNotifier()

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
            NectarUtil.getRepositoryFolder(context), UuidGenerator(),
            notifier
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
            ReceipeStepAlimentStateRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ReceipeStepReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context),
            notifier
        ),
        EntityType.MEAL to MealSynchronizer(
            MealRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            MealAlimentRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            MealReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context),
            notifier
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
            NectarUtil.getRepositoryFolder(context),
            notifier
        ),
        EntityType.STRING_KEY to StringKeySynchronizer(
            StringKeyRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            StringKeyValueRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        ),
        EntityType.SHOPPING_LIST to ShoppingListSynchronizer(
            ShoppingListRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            ShoppingListAlimentStateRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
            NectarUtil.getRepositoryFolder(context)
        )
    )

    private fun mustBeSync(gitRepository: GitRepository, entityType: EntityType, uuid: String): Boolean {
        val s = gitRepository.selectiveSynchronization ?: return true

        return when(s.synchronizationType) {
            GitSelectiveSynchronization.SynchronizationType.BOOK -> {
                //s.bookUuid
                true
            }
            GitSelectiveSynchronization.SynchronizationType.SELECTIVE -> {
                entityType in s.getSelectiveMap()[s.level]!!
            }
        }
    }

    private fun fromGitToDbRepository(gitRepository: GitRepository, diff: GitManager.DiffResult) {
        val sortedUpdates = diff.updates.sortedWith(compareBy { it.first.ordinal })
        val sortedDeletes = diff.deletes.sortedWith(compareByDescending { it.first.ordinal })

        // create and update
        // we don't add entity deleted recently
        val updates = DatabaseUpdateRepository(context).list()
        var deleteUpdates = mutableListOf<String>()
        for(u in updates) {
            if(u.updateType == UpdateType.DELETE)
                deleteUpdates.add(u.entityUuid)
        }
        for((dt, uuid) in sortedUpdates) {
            if(!mustBeSync(gitRepository, dt, uuid))
                continue
            if(uuid in deleteUpdates)
                continue

            try {
                synchronizerMap[dt]?.fromGitToDb(gitRepository.name, uuid)
            }
            catch(e: Exception) {
                Log.e("nectar", "fromGitToDb error in repo ${gitRepository.name}: ${dt.folderName}/$uuid can't be parsed. Error: $e")
            }
        }

        // delete
        for((dt, uuid) in sortedDeletes) {
            if(!mustBeSync(gitRepository, dt, uuid))
                continue

            try {
                synchronizerMap[dt]?.fromGitDeleteInDb(uuid)
            }
            catch(e: Exception) {
                Log.e("nectar", "can't delete $uuid. Error: $e")
            }
        }
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

        if(gitRepository.rescan) {
            fromGitToDbRepository(gitRepository, manager.rescan())
        }
        else if ((currentTimestamp - gitRepository.lastCheck >= gitRepository.frequency) && manager.fetch()) {
            fromGitToDbRepository(gitRepository, manager.diff())
            manager.sync()
        }

        manager.close()
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
        manager.fetch()
        manager.sync()

        val rDatabaseUpdate = DatabaseUpdateRepository(context)
        val updates = if(gitRepository.rescan) rDatabaseUpdate.rescan() else rDatabaseUpdate.list()

        var updated = false
        for(u in updates) {
            if(!mustBeSync(gitRepository, u.entityType, u.entityUuid))
                continue

            // Force delete fixes a bug because the entity is never deleted.
            // Indeed the entity is always recreated in synchronizeFromGitToDb.
            synchronizerMap[u.entityType]?.fromDbToGit(
                gitRepository.name,
                u.entityUuid,
                u.updateType == UpdateType.DELETE
            )

            updated = true
        }

        if(updated && manager.hasNewDiff()) {
            Log.i("Nectar", "Push updates to ${gitRepository.name}")
            manager.addAll()
            manager.commit()
            manager.push()
        }

        manager.close()

        // Clean updates
        for(u in updates) {
            rDatabaseUpdate.delete(u)
        }
    }

    @Synchronized
    fun exec(gitRepositoryUuid: String? = null) {
        if(!NectarUtil.isNetworkAvailable(context))
            return

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

        notifier.prepare()
        for(gitRepository in gitRepositories) {
            try {
                synchronizeFromGitToDb(gitRepository, currentTimestamp, baseRepositoryFolder)
                synchronizeFromDbToGit(gitRepository, baseRepositoryFolder)
            } catch (e: TransportException) {
                Log.e("Nectar", "Can't connect to repository ${gitRepository.name}")
                Log.e("Nectar", NectarUtil.exceptionToStacktrace(e))
                continue
            }

            gitRepository.lastCheck = currentTimestamp
            gitRepository.rescan = false
            rGitRepository.update(gitRepository)
        }
        notifier.notify(context)
    }
}