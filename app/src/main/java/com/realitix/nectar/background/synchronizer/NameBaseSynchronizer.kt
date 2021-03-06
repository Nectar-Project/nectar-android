package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.UuidInterface
import com.realitix.nectar.repository.NameGenericRepository
import java.io.File

abstract class NameBaseSynchronizer<ERaw, E: UuidInterface>(
    private val rName: NameGenericRepository<ERaw, E>,
    baseRepositoryFolder: File
): BaseSynchronizer<NameBaseSynchronizer.ParseResult>(baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String
    )

    abstract fun getNew(uuid: String, nameUuid: String): ERaw
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)
    override fun isEntityExists(uuid: String): Boolean = rName.get(uuid) != null

    override fun updateDb(parseResult: ParseResult) {
        // Create name only if not exists
        if(rName.get(parseResult.uuid) == null) {
            rName.insert(getNew(parseResult.uuid, parseResult.nameUuid))
        }
    }

    override fun populateParseResult(uuid: String): ParseResult = ParseResult(uuid, rName.getNameUuid(uuid))
}