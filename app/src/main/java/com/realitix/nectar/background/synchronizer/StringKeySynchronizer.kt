package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.util.EntityType
import java.io.File

class StringKeySynchronizer(
    repository: StringKeyRepository,
    baseRepositoryFolder: File
): BaseSynchronizer<StringKeySynchronizer.ParseResult, StringKeyRepository>(repository, baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val values: Map<String, String>
    )

    override fun getEntityType(): EntityType = EntityType.STRING_KEY
    override fun getParseResult(repositoryName: String, uuid: String): ParseResult = getInnerParseResult(repositoryName, uuid)

    override fun updateDb(repo: StringKeyRepository, parseResult: ParseResult) {
        // Create name only if not exists
        if(repo.get(parseResult.uuid) == null) {
            repo.insert(StringKeyRaw(parseResult.uuid))
        }

        // names
        for((lang, jsonValue) in parseResult.values) {
            val value = repo.getValue(parseResult.uuid, lang)
            if(value == null) {
                repo.insertValue(StringKeyValueRaw(parseResult.uuid, lang, jsonValue))
            }
            else if(value.value != jsonValue){
                repo.updateValue(StringKeyValueRaw(parseResult.uuid, lang, jsonValue))
            }
        }
    }

    override fun populateParseResult(repo: StringKeyRepository, uuid: String): ParseResult {
        val name = repo.get(uuid)!!

        val names = mutableMapOf<String, String>()
        for(n in name.strings) {
            names[n.language] = n.value
        }

        return ParseResult(name.uuid, names)
    }
}