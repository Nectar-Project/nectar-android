package com.realitix.mealassistant.work.parser

import android.content.Context
import com.beust.klaxon.JsonReader
import java.io.File
import java.io.FileReader
import java.io.StringReader

class AlimentParser {
    companion object {
        fun add(context: Context, repositoryName: String, uuid: String) {
            val repoFolder = File(context.filesDir, repositoryName)
            val alimentFolder = File(repoFolder, "aliments")
            var alimentFile = File(alimentFolder, uuid)
            JsonReader(FileReader(alimentFile)).use { reader ->
                reader.beginObject() {
                    var name: String? = null
                    var age: Int? = null
                    var flag: Boolean? = null
                    var array: List<Any> = arrayListOf<Any>()
                    var obj1: JsonObject? = null
                    while (reader.hasNext()) {
                        val readName = reader.nextName()
                        when (readName) {
                            "name" -> name = reader.nextString()
                            "age" -> age = reader.nextInt()
                            "flag" -> flag = reader.nextBoolean()
                            "array" -> array = reader.nextArray()
                            "obj1" -> obj1 = reader.nextObject()
                            else -> Assert.fail("Unexpected name: $readName")
                        }
                    }
                }
            }

        }
    }
}