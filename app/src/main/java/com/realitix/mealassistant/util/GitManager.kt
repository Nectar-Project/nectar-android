package com.realitix.mealassistant.util

import android.util.Log
import com.realitix.mealassistant.database.entity.GitCredentials
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ResetCommand
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.eclipse.jgit.treewalk.CanonicalTreeParser
import java.io.File


class GitManager(private val repoDir: File, private val url: String, private val credentials: GitCredentials?) {
    enum class DiffType {
        UNKNOW, ALIMENT, RECEIPE, MEAL, MEASURE, TAG
    }

    private fun mapToDiffType(str: String): DiffType {
        return when(str) {
            "aliments" -> DiffType.ALIMENT
            "receipes" -> DiffType.RECEIPE
            "meals" -> DiffType.MEAL
            "measures" -> DiffType.MEASURE
            "tags" -> DiffType.TAG
            else -> DiffType.UNKNOW
        }
    }

    private fun splitPath(str: String): List<String> {
        val l = str.split("/")
        if(l.size != 2) {
            return listOf("unknow", "")
        }
        return l
    }

    // Contains list of uuid
    class DiffResult(
        val hasResult: Boolean,
        val adds: List<Pair<DiffType, String>>,
        val updates: List<Pair<DiffType, String>>,
        val deletes: List<Pair<DiffType, String>>
    )

    private val git: Git by lazy {
        if(!repoDir.exists()) {
            val git = Git.cloneRepository()
                .setURI(url)
                .setDirectory(repoDir)

            if( credentials != null ) {
                git.setCredentialsProvider(UsernamePasswordCredentialsProvider(credentials.username, credentials.password))
            }

            git.call()
        }
        else {
            val builder = FileRepositoryBuilder()

            val repo = builder.setGitDir(File(repoDir, ".git"))
                .readEnvironment()
                .findGitDir()
                .setMustExist(true)
                .build()

            Git(repo)
        }
    }

    // Return true if remote has new commits
    fun fetch(): Boolean = !git.fetch().call().trackingRefUpdates.isEmpty()

    // Sync master and origin/master
    fun sync() {
        git.reset().setMode(ResetCommand.ResetType.HARD).setRef("refs/remotes/origin/master").call()
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

        val hasResult = entries.size > 0
        val adds: ArrayList<Pair<DiffType, String>> = ArrayList()
        val updates: ArrayList<Pair<DiffType, String>> = ArrayList()
        val deletes: ArrayList<Pair<DiffType, String>> = ArrayList()
        for(entry in entries) {
            when(entry.changeType) {
                DiffEntry.ChangeType.ADD -> {
                    val splitted = splitPath(entry.newPath)
                    adds.add(mapToDiffType(splitted[0]) to splitted[1])
                }
                DiffEntry.ChangeType.DELETE -> {
                    val splitted = splitPath(entry.oldPath)
                    deletes.add(mapToDiffType(splitted[0]) to splitted[1])
                }
                DiffEntry.ChangeType.MODIFY -> {
                    val splitted = splitPath(entry.newPath)
                    updates.add(mapToDiffType(splitted[0]) to splitted[1])
                }
                else -> {}
            }
        }

        return DiffResult(hasResult, adds, updates, deletes)
    }

    private fun getBranch(rev: String): CanonicalTreeParser {
        val treeParser = CanonicalTreeParser()
        val treeId = git.repository.resolve("$rev^{tree}")
        git.repository.newObjectReader().use { reader -> treeParser.reset(reader, treeId) }
        return treeParser
    }
}