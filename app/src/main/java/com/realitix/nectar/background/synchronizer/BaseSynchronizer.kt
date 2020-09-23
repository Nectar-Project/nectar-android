package com.realitix.nectar.background.synchronizer

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil
import java.io.File

abstract class BaseSynchronizer<P>(
    private val baseRepositoryFolder: File
): SynchronizerInterface {

    abstract fun getEntityType(): EntityType
    abstract fun getParseResult(repositoryName: String, uuid: String): P
    abstract fun updateDb(parseResult: P)
    abstract fun populateParseResult(uuid: String): P
    abstract fun isEntityExists(uuid: String): Boolean

    private fun getEntityFile(repositoryName: String, uuid: String): File {
        val repoFolder = File(baseRepositoryFolder, repositoryName)
        val entityFolder = File(repoFolder, getEntityType().folderName)
        return File(entityFolder, uuid)
    }

    fun readFile(repositoryName: String, uuid: String): String = getEntityFile(repositoryName, uuid).readText()

    private fun writeFile(repositoryName: String, uuid: String, out: P) {
        NectarUtil.writeToJsonFile(getEntityFile(repositoryName, uuid), out!!)
    }

    inline fun <reified P> parse(json: String): P = Klaxon().parse<P>(json)!!
    inline fun <reified P> getInnerParseResult(repositoryName: String, uuid: String): P = parse(readFile(repositoryName, uuid))
    override fun fromGitToDb(gitRepositoryName: String, uuid: String) = updateDb(getParseResult(gitRepositoryName, uuid))
    override fun fromDbToGit(gitRepositoryName: String, uuid: String) {
        if(isEntityExists(uuid)) {
            writeFile(gitRepositoryName, uuid, populateParseResult(uuid))
        }
        else {
            getEntityFile(gitRepositoryName, uuid).delete()
        }
    }
}