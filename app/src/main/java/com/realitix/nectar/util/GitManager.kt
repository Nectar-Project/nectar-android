package com.realitix.nectar.util

import android.util.Log
import com.realitix.nectar.database.entity.GitCredentials
import com.realitix.nectar.util.NectarUtil.Companion.isUuidValid
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.internal.JGitText
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.revwalk.DepthWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Stream


class GitManager(private val repoDir: File, private val url: String, private val credentials: GitCredentials?) {
    private fun mapToDiffType(str: String): EntityType = EntityType.values().find { it.folderName == str } ?: EntityType.UNKNOW

    private fun splitPath(str: String): List<String> {
        val l = str.split("/")
        if(l.size != 2) {
            return listOf("unknow", "")
        }
        return l
    }

    private fun initGitRepository(): Git {
        if(!repoDir.exists()) {
            val git = Git.cloneRepository()
                .setURI(url)
                .setDirectory(repoDir)

            if( credentials != null ) {
                git.setCredentialsProvider(UsernamePasswordCredentialsProvider(credentials.username, credentials.password))
            }

            return git.call()
        }

        val builder = FileRepositoryBuilder()
        val repo = builder.setGitDir(File(repoDir, ".git"))
            .readEnvironment()
            .findGitDir()
            .setMustExist(true)
            .build()

        return Git(repo)
    }

    // Contains list of uuid
    class DiffResult(
        val updates: List<Pair<EntityType, String>>,
        val deletes: List<Pair<EntityType, String>>
    )

    private val git: Git = initGitRepository()

    // Return true if remote has new commits
    fun fetch(): Boolean = !git.fetch().call().trackingRefUpdates.isEmpty()

    // Sync master and origin/master
    fun sync() {
        git.reset().setMode(ResetCommand.ResetType.HARD).setRef("refs/remotes/origin/master").call()
    }

    // Return a diff of all files
    fun rescan(): DiffResult {
        val updates: ArrayList<Pair<EntityType, String>> = ArrayList()
        val deletes: ArrayList<Pair<EntityType, String>> = ArrayList()

        for (entityType in EntityType.values()) {
            if (entityType == EntityType.UNKNOW)
                continue

            val stream: Stream<Path> =  try {
                Files.list(File(repoDir, entityType.folderName).toPath())
            }
            catch(e: Exception) {
                Log.w("Nectar", "Folder ${entityType.folderName} not found in $repoDir")
                continue
            }

            stream.forEach {
                val uuid = it.toFile().name
                if(!isUuidValid(uuid)) {
                    Log.w("Nectar", "Filename $uuid is not a valid uuid")
                }
                else {
                    updates.add(entityType to uuid)
                }
            }
        }

        return DiffResult(updates, deletes)
    }

    // Return diff between master and origin/master
    fun diff(): DiffResult {
        val master = getBranch("master")
        val originMaster = getBranch("origin/master")
        val entries = git.diff()
            .setOldTree(master)
            .setNewTree(originMaster)
            .setShowNameAndStatusOnly(true)
            .call()

        val updates: ArrayList<Pair<EntityType, String>> = ArrayList()
        val deletes: ArrayList<Pair<EntityType, String>> = ArrayList()
        for(entry in entries) {
            when(entry.changeType) {
                DiffEntry.ChangeType.ADD, DiffEntry.ChangeType.MODIFY -> {
                    val splitted = splitPath(entry.newPath)
                    updates.add(mapToDiffType(splitted[0]) to splitted[1])
                }
                DiffEntry.ChangeType.DELETE -> {
                    val splitted = splitPath(entry.oldPath)
                    deletes.add(mapToDiffType(splitted[0]) to splitted[1])
                }
                else -> {}
            }
        }

        return DiffResult(updates, deletes)
    }

    fun hasNewDiff(): Boolean {
        return git.diff().setShowNameAndStatusOnly(true).call().isNotEmpty()
    }

    private fun getBranch(rev: String): CanonicalTreeParser {
        val treeParser = CanonicalTreeParser()
        val treeId = git.repository.resolve("$rev^{tree}")
        treeParser.reset(git.repository.newObjectReader(), treeId)
        return treeParser
    }

    fun addAll() {
        // Add modified and removed files
        git.add().setUpdate(true).addFilepattern(".").call()
        // Add new files
        git.add().setUpdate(false).addFilepattern(".").call()
    }

    fun commit() {
        git.commit().setMessage("Commit all changes").call()
    }

    fun push(): Boolean {
        if(credentials == null) {
            Log.w("Nectar", "Can't push without credentials")
            return false
        }
        git.push()
            .setCredentialsProvider(UsernamePasswordCredentialsProvider(credentials.username, credentials.password))
            .call()

        return true
    }

    fun clean() {
        Files.list(repoDir.toPath()).forEach {
            val f = it.toFile()
            if(f.name != ".git") {
                f.deleteRecursively()
            }
        }
    }
}