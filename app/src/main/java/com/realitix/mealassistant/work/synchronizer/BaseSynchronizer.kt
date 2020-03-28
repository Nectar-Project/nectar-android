package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.beust.klaxon.Klaxon
import com.realitix.mealassistant.util.EntityType
import java.io.File

abstract class BaseSynchronizer<P, R>: SynchronizerInterface {

    abstract fun getEntityType(): EntityType
    abstract fun getRepository(context: Context): R

    fun readFile(context: Context, repositoryName: String, uuid: String): String {
        val repoFolder = File(context.filesDir, repositoryName)
        val entityFolder = File(repoFolder, getEntityType().folderName)
        val entityFile = File(entityFolder, uuid)
        return entityFile.readText()
    }

    inline fun <reified P> parse(json: String): P {
        return Klaxon().parse<P>(json)!!
    }

    inline fun <reified P> getParseResult(context: Context, repositoryName: String, uuid: String): P {
        return parse(readFile(context, repositoryName, uuid))
    }
}