package com.realitix.nectar.work.synchronizer

import com.beust.klaxon.Klaxon
import com.realitix.nectar.util.EntityType
import java.io.File

abstract class BaseSynchronizer<P, R>(
    private val repository: R,
    private val baseRepositoryFolder: File
): SynchronizerInterface {

    abstract fun getEntityType(): EntityType
    abstract fun getParseResult(repositoryName: String, uuid: String): P
    abstract fun updateDb(repo: R, parseResult: P)

    fun readFile(repositoryName: String, uuid: String): String {
        val repoFolder = File(baseRepositoryFolder, repositoryName)
        val entityFolder = File(repoFolder, getEntityType().folderName)
        val entityFile = File(entityFolder, uuid)
        return entityFile.readText()
    }

    inline fun <reified P> parse(json: String): P {
        return Klaxon().parse<P>(json)!!
    }

    inline fun <reified P> getInnerParseResult(repositoryName: String, uuid: String): P {
        return parse(readFile(repositoryName, uuid))
    }

    override fun fromGitToDb(gitRepositoryName: String, uuid: String) {
        updateDb(repository, getParseResult(gitRepositoryName, uuid))
    }
}