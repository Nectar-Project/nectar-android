package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.beust.klaxon.Klaxon
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.MealUtil.Companion.getRepositoryFolder
import java.io.File

abstract class BaseSynchronizer<P, R>(private val context: Context, private val repository: R): SynchronizerInterface {

    abstract fun getEntityType(): EntityType
    abstract fun getParseResult(context: Context, repositoryName: String, uuid: String): P
    abstract fun updateDb(repo: R, parseResult: P)

    fun readFile(context: Context, repositoryName: String, uuid: String): String {
        val repoFolder = getRepositoryFolder(context, repositoryName)
        val entityFolder = File(repoFolder, getEntityType().folderName)
        val entityFile = File(entityFolder, uuid)
        return entityFile.readText()
    }

    inline fun <reified P> parse(json: String): P {
        return Klaxon().parse<P>(json)!!
    }

    inline fun <reified P> getInnerParseResult(context: Context, repositoryName: String, uuid: String): P {
        return parse(readFile(context, repositoryName, uuid))
    }

    override fun fromGitToDb(gitRepositoryName: String, uuid: String) {
        updateDb(repository, getParseResult(context, gitRepositoryName, uuid))
    }
}