package com.realitix.nectar.background.synchronizer

import com.realitix.nectar.background.DummyNotifier
import com.realitix.nectar.background.NotifierInterface
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
    private val uuidGenerator: UuidGeneratorInterface,
    private val notifier: NotifierInterface = DummyNotifier()
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
    override fun isEntityExists(uuid: String): Boolean = rAliment.get(uuid) != null

    override fun fromGitDeleteInDb(uuid: String) {
        val aliment = rAliment.get(uuid)!!

        // delete states
        for(state in aliment.getStates(rAlimentState)) {
            for(measure in state.measures) {
                rAlimentStateMeasure.delete(measure)
            }
            rAlimentState.delete(state)
        }

        // delete tags
        for(tag in aliment.tags) {
            rAlimentTag.delete(tag)
        }

        // delete image
        for(image in aliment.images) {
            rAlimentImage.delete(image)
        }

        // delete aliment
        rAliment.delete(aliment)
    }

    override fun updateDb(parseResult: ParseResult) {

        // Create aliment only if not exists
        if(rAliment.get(parseResult.uuid) == null) {
            rAliment.insert(AlimentRaw(parseResult.uuid, parseResult.nameUuid))
            notifier.addNotification("Ajout d'un aliment")
        }

        // images
        for(imageUuid in parseResult.images) {
            if(rAlimentImage.get(parseResult.uuid, imageUuid) == null) {
                rAlimentImage.insert(AlimentImageRaw(parseResult.uuid, imageUuid))
            }
        }

        // tags
        for(tagUuid in parseResult.tags) {
            if(rAlimentTag.get(parseResult.uuid, tagUuid) == null) {
                rAlimentTag.insert(AlimentTagRaw(parseResult.uuid, tagUuid))
            }
        }

        // states
        for((stateUuid, state) in parseResult.states) {
            // nutrition
            var alimentStateUuid = uuidGenerator.generateUuid()
            if(rAlimentState.get(parseResult.uuid, stateUuid) == null) {
                rAlimentState.insert(AlimentStateRaw(alimentStateUuid, parseResult.uuid, stateUuid, state.nutrition))
            }
            else {
                alimentStateUuid = rAlimentState.get(parseResult.uuid, stateUuid)!!.uuid
            }

            for((measureUuid, quantity) in state.measures) {
                if(rAlimentStateMeasure.get(alimentStateUuid, measureUuid) == null) {
                    rAlimentStateMeasure.insert(AlimentStateMeasureRaw(alimentStateUuid, measureUuid, quantity))
                }
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
        for(state in aliment.getStates(rAlimentState)) {
            val measures = mutableMapOf<String, Int>()
            for(m in state.measures) {
                measures[m.measureUuid] = m.quantity
            }
            states[state.stateUuid] = State(measures, state.nutrition)
        }

        return ParseResult(aliment.uuid, aliment.nameUuid, images, tags, states)
    }
}