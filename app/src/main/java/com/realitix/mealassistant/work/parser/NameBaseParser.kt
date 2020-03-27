package com.realitix.mealassistant.work.parser

import android.content.Context
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.AlimentRepository
import com.realitix.mealassistant.repository.NameRepositoryInterface
import com.realitix.mealassistant.repository.StateRepository
import com.realitix.mealassistant.repository.TagRepository
import java.io.File
import java.io.FileReader

abstract class NameBaseParser<U, V>: ParserInterface {
    class ParseResult(
        val uuid: String,
        val names: Map<String, String>
    )

    abstract fun getRepository(context: Context): NameRepositoryInterface<U, V>
    abstract fun getNew(uuid: String): U
    abstract fun getNewName(uuid: String, lang: String, name: String): V
    abstract fun getSourceFolder(): String

    override fun update(context: Context, repositoryName: String, uuid: String) {
        val repo = getRepository(context)
        val parseResult = parse(context, repositoryName, uuid)

        // Create tag only if not exists
        if(repo.getRaw(parseResult.uuid) == null) {
            repo.insert(getNew(parseResult.uuid))
        }

        // add names
        for((lang, name) in parseResult.names) {
            repo.insertName(getNewName(parseResult.uuid, lang, name))
        }
    }

    private fun parse(context: Context, repositoryName: String, uuid: String): ParseResult {
        val repoFolder = File(context.filesDir, repositoryName)
        val sourceFolder = File(repoFolder, getSourceFolder())
        val finalFile = File(sourceFolder, uuid)
        val root = Parser().parse(FileReader(finalFile)) as JsonObject

        // uuid
        val jsonUuid = root.string("uuid")!!

        // names
        val names: MutableMap<String, String> = mutableMapOf()
        for(jsonName in root.obj("names")!!.entries) {
            names[jsonName.key] = jsonName.value as String
        }

        return ParseResult(jsonUuid, names)
    }
}