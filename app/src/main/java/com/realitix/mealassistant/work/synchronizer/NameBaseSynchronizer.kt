package com.realitix.mealassistant.work.synchronizer

import android.content.Context
import com.realitix.mealassistant.repository.NameRepositoryInterface

abstract class NameBaseSynchronizer<U, V>: BaseSynchronizer<NameBaseSynchronizer.ParseResult, NameRepositoryInterface<U, V>>() {
    class ParseResult(
        val uuid: String,
        val names: Map<String, String>
    )

    abstract fun getNew(uuid: String): U
    abstract fun getNewName(uuid: String, lang: String, name: String): V

    override fun getParseResult(context: Context, repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(context, repositoryName, uuid)

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
}