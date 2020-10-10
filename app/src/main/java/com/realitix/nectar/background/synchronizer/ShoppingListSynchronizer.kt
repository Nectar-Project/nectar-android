package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.EntityType
import java.io.File

class ShoppingListSynchronizer(
    private val rShoppingList: ShoppingListRepository,
    private val rShoppingListAlimentState: ShoppingListAlimentStateRepository,
    baseRepositoryFolder: File
): BaseSynchronizer<ShoppingListSynchronizer.ParseResult>(baseRepositoryFolder) {
    data class ParseResult(
        val uuid: String,
        val beginTimestamp: Long,
        val endTimestamp: Long,
        val alimentStates: Map<String, AlimentStateParseResult>
    )

    data class AlimentStateParseResult(
        val weight: Int,
        val checked: Boolean
    )

    override fun getEntityType(): EntityType = EntityType.SHOPPING_LIST
    override fun getParseResult(repositoryName: String, uuid: String) = getInnerParseResult<ParseResult>(repositoryName, uuid)
    override fun isEntityExists(uuid: String): Boolean = rShoppingList.get(uuid) != null

    override fun fromGitDeleteInDb(uuid: String) {
        val shopping = rShoppingList.get(uuid)!!

        for(a in shopping.aliments) {
            rShoppingListAlimentState.delete(a)
        }

        rShoppingList.delete(shopping)
    }

    override fun updateDb(parseResult: ParseResult) {
        // Create meal only if not exists
        if(rShoppingList.get(parseResult.uuid) == null) {
            rShoppingList.insert(ShoppingListRaw(parseResult.uuid, parseResult.beginTimestamp, parseResult.endTimestamp))
        }

        // alimentStates
        for ((alimentStateUuid, r) in parseResult.alimentStates) {
            if(rShoppingListAlimentState.get(parseResult.uuid, alimentStateUuid) == null) {
                rShoppingListAlimentState.insert(ShoppingListAlimentStateRaw(parseResult.uuid, alimentStateUuid, r.weight, r.checked))
            }
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val s = rShoppingList.get(uuid)!!

        val alimentStates = mutableMapOf<String, AlimentStateParseResult>()
        for(a in s.aliments) {
            alimentStates[a.alimentStateUuid] = AlimentStateParseResult(a.weight, a.checked)
        }

        return ParseResult(s.uuid, s.beginTimestamp, s.endTimestamp, alimentStates)
    }
}