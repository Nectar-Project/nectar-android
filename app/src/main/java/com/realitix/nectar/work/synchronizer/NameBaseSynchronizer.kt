package com.realitix.nectar.work.synchronizer

import com.realitix.nectar.repository.NameRepositoryInterface
import java.io.File

abstract class NameBaseSynchronizer<U, V>(repository: NameRepositoryInterface<U, V>, baseRepositoryFolder: File):
    BaseSynchronizer<NameBaseSynchronizer.ParseResult, NameRepositoryInterface<U, V>>(repository, baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val names: Map<String, String>
    )

    abstract fun getNew(uuid: String): U
    abstract fun getNewName(uuid: String, lang: String, name: String): V
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)

    override fun updateDb(repo: NameRepositoryInterface<U, V>, parseResult: ParseResult) {
        // Create tag only if not exists
        if(repo.getRaw(parseResult.uuid) == null) {
            repo.insert(getNew(parseResult.uuid))
        }

        // add names
        for((lang, name) in parseResult.names) {
            repo.insertName(getNewName(parseResult.uuid, lang, name))
        }
    }

    override fun populateParseResult(
        repo: NameRepositoryInterface<U, V>,
        uuid: String
    ): ParseResult = ParseResult(uuid, repo.getNamesMap(uuid))
}