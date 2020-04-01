package com.realitix.nectar.work.synchronizer

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import com.realitix.nectar.util.EntityType
import java.io.File

abstract class BaseSynchronizer<P, R>(
    private val repository: R,
    private val baseRepositoryFolder: File
): SynchronizerInterface {

    abstract fun getEntityType(): EntityType
    abstract fun getParseResult(repositoryName: String, uuid: String): P
    abstract fun updateDb(repo: R, parseResult: P)
    abstract fun populateParseResult(repo: R, uuid: String): P

    private fun getEntityFile(repositoryName: String, uuid: String): File {
        val repoFolder = File(baseRepositoryFolder, repositoryName)
        val entityFolder = File(repoFolder, getEntityType().folderName)
        return File(entityFolder, uuid)
    }

    fun readFile(repositoryName: String, uuid: String): String = getEntityFile(repositoryName, uuid).readText()

    fun writeFile(repositoryName: String, uuid: String, out: P) {
        val entityFile = getEntityFile(repositoryName, uuid)
        val builder = StringBuilder(Klaxon().toJsonString(out!!))
        val result = (Parser().parse(builder) as JsonObject).toJsonString(true)
        entityFile.writeText(result)
        println("Result: ${result}")
    }

    inline fun <reified P> parse(json: String): P = Klaxon().parse<P>(json)!!
    inline fun <reified P> getInnerParseResult(repositoryName: String, uuid: String): P = parse(readFile(repositoryName, uuid))
    override fun fromGitToDb(gitRepositoryName: String, uuid: String) = updateDb(repository, getParseResult(gitRepositoryName, uuid))
    override fun fromDbToGit(gitRepositoryName: String, uuid: String) = writeFile(gitRepositoryName, uuid, populateParseResult(repository, uuid))
}