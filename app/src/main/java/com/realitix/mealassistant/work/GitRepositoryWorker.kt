package com.realitix.mealassistant.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.realitix.mealassistant.database.entity.GitRepository
import com.realitix.mealassistant.repository.GitRepoRepository
import com.realitix.mealassistant.util.GitManager
import com.realitix.mealassistant.work.parser.AlimentParser
import java.io.File

class GitRepositoryWorker(val context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {
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
                        // add
                        for((dt, uuid) in diff.adds) {
                            when(dt) {
                                GitManager.DiffType.ALIMENT -> AlimentParser.add(context, repo.name, uuid)
                            }
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