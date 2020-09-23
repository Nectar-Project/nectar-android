package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class StringKeySynchronizer(
    private val rStringKey: StringKeyRepository,
    private val rStringKeyValue: StringKeyValueRepository,
    baseRepositoryFolder: File
): BaseSynchronizer<StringKeySynchronizer.ParseResult>(baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val values: Map<String, String>
    )

    override fun getEntityType(): EntityType = EntityType.STRING_KEY
    override fun getParseResult(repositoryName: String, uuid: String): ParseResult = getInnerParseResult(repositoryName, uuid)
    override fun isEntityExists(uuid: String): Boolean = rStringKey.get(uuid) != null

    override fun updateDb(parseResult: ParseResult) {
        // Create name only if not exists
        if(rStringKey.get(parseResult.uuid) == null) {
            rStringKey.insert(StringKeyRaw(parseResult.uuid))
        }

        // names
        for((lang, jsonValue) in parseResult.values) {
            val value = rStringKeyValue.get(parseResult.uuid, lang)
            if(value == null) {
                rStringKeyValue.insert(StringKeyValueRaw(parseResult.uuid, lang, jsonValue))
            }
            else if(value.value != jsonValue){
                rStringKeyValue.update(StringKeyValueRaw(parseResult.uuid, lang, jsonValue))
            }
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val name = rStringKey.get(uuid)!!

        val names = mutableMapOf<String, String>()
        for(n in name.strings) {
            names[n.language] = n.value
        }

        return ParseResult(name.uuid, names)
    }
}