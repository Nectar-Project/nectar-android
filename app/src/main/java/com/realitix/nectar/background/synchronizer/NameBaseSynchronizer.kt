package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.repository.NameRepositoryInterface
import java.io.File

abstract class NameBaseSynchronizer<U>(repository: NameRepositoryInterface<U>, baseRepositoryFolder: File):
    BaseSynchronizer<NameBaseSynchronizer.ParseResult, NameRepositoryInterface<U>>(repository, baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String
    )

    abstract fun getNew(uuid: String, nameUuid: String): U
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)

    override fun updateDb(repo: NameRepositoryInterface<U>, parseResult: ParseResult) {
        // Create tag only if not exists
        if(repo.getRaw(parseResult.uuid) == null) {
            repo.insert(getNew(parseResult.uuid, parseResult.nameUuid))
        }
    }

    override fun populateParseResult(
        repo: NameRepositoryInterface<U>,
        uuid: String
    ): ParseResult = ParseResult(uuid, repo.getNameUuid(uuid))
}