package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.UuidGeneratorInterface
import java.io.File

class AlimentSynchronizer(
    private val rAliment: AlimentRepository,
    private val rAlimentImage: AlimentImageRepository,
    private val rAlimentTag: AlimentTagRepository,
    private val rAlimentState: AlimentStateRepository,
    private val rAlimentStateMeasure: AlimentStateMeasureRepository,
    baseRepositoryFolder: File,
    private val uuidGenerator: UuidGeneratorInterface
): BaseSynchronizer<AlimentSynchronizer.ParseResult>(baseRepositoryFolder) {
    class ParseResult(
        val uuid: String,
        val nameUuid: String,
        val images: List<String>,
        val tags: List<String>,
        val states: Map<String, State>
    )

    class State(val measures: Map<String, Int>, val nutrition: Nutrition)

    override fun getEntityType(): EntityType = EntityType.ALIMENT
    override fun getParseResult(repositoryName: String, uuid: String): ParseResult = getInnerParseResult(repositoryName, uuid)

    override fun updateDb(parseResult: ParseResult) {
        // Create aliment only if not exists
        if(rAliment.get(parseResult.uuid) == null) {
            rAliment.insert(AlimentRaw(parseResult.uuid, parseResult.nameUuid))
        }

        // images
        for(imageUuid in parseResult.images) {
            rAlimentImage.insert(AlimentImageRaw(parseResult.uuid, imageUuid))
        }

        // tags
        for(tagUuid in parseResult.tags) {
            rAlimentTag.insert(AlimentTagRaw(parseResult.uuid, tagUuid))
        }

        // states
        for((stateUuid, state) in parseResult.states) {
            // nutrition
            val alimentStateUuid = uuidGenerator.generateUuid()
            rAlimentState.insert(AlimentStateRaw(alimentStateUuid, parseResult.uuid, stateUuid, state.nutrition))
            for((measureUuid, quantity) in state.measures) {
                rAlimentStateMeasure.insert(AlimentStateMeasureRaw(alimentStateUuid, measureUuid, quantity))
            }
        }
    }

    override fun populateParseResult(uuid: String): ParseResult {
        val aliment = rAliment.get(uuid)!!

        val images = mutableListOf<String>()
        for(a in aliment.images) {
            images.add(a.imageUuid)
        }

        val tags = mutableListOf<String>()
        for(a in aliment.tags) {
            tags.add(a.tagUuid)
        }

        val states = mutableMapOf<String, State>()
        for(state in aliment.states) {
            val measures = mutableMapOf<String, Int>()
            for(m in state.measures) {
                measures[m.measureUuid] = m.quantity
            }
            states[state.stateUuid] = State(measures, state.nutrition)
        }

        return ParseResult(aliment.uuid, aliment.nameUuid, images, tags, states)
    }
}