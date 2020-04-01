package com.realitix.mealassistant

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.*
import com.realitix.mealassistant.util.*
import com.realitix.mealassistant.work.synchronizer.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import java.nio.file.Files


private const val TEST_RESSOURCES_DIR = "src/test/resources/synchronizer"
private const val TEST_DATA_REPOSITORY_URL = "https://github.com/Nectar-Project/nectar-data.git"
private const val TEST_REPOSITORY_NAME = "test_repository"

@RunWith(MockitoJUnitRunner::class)
class SynchronizerUnitTest {
    @Mock
    private lateinit var context: Context

    private fun getRepositoryFolder(): File = File(TEST_RESSOURCES_DIR)

    @Test
    fun parseDataRepository() {
        // Clone repo
        val repositoryName = java.util.UUID.randomUUID().toString()
        val ressourceDir = File(TEST_RESSOURCES_DIR)
        val repositoryFile = File(ressourceDir, repositoryName)
        GitManager(repositoryFile, TEST_DATA_REPOSITORY_URL, null).clone()

        // Loop through each file and parse it
        val synchronizerMap = mapOf(
            EntityType.STATE to StateSynchronizer(StateRepository(context), getRepositoryFolder()),
            EntityType.TAG to TagSynchronizer(TagRepository(context), getRepositoryFolder()),
            EntityType.MEASURE to MeasureSynchronizer(MeasureRepository(context), getRepositoryFolder()),
            EntityType.ALIMENT to AlimentSynchronizer(AlimentRepository(context), getRepositoryFolder(), UuidGenerator()),
            EntityType.UTENSIL to UtensilSynchronizer(UtensilRepository(context), getRepositoryFolder()),
            EntityType.RECEIPE to ReceipeSynchronizer(ReceipeRepository(context), getRepositoryFolder()),
            EntityType.MEAL to MealSynchronizer(MealRepository(context), getRepositoryFolder())
        )
        try {
            for (entityType in EntityType.values()) {
                //if (entityType == EntityType.UNKNOW || entityType == EntityType.IMAGE)
                //    continue
                Files.list(File(repositoryFile, entityType.folderName).toPath()).forEach {
                    val uuid = it.toFile().name
                    println("Parse path: $it")
                    if(synchronizerMap[entityType] != null) {
                        (synchronizerMap[entityType] ?: error("")).getParseResult(repositoryName, uuid)
                    }
                }
            }
        }
        catch(e: Exception) {
            println("The last uuid can't be parsed !!")
            println(e)
            repositoryFile.deleteRecursively()
            Assert.assertTrue(false)
        }
        repositoryFile.deleteRecursively()
    }


    @Test
    fun mealGitToDb() {
        val mealUuid = "eaa61552-e57c-49fa-a028-4ddc5b8d48c0"
        val alimentUuid = "a7a12c60-1604-48ee-9991-0e4baa08006d"
        val receipeUuid = "592bfb6a-0519-4ba6-855c-f4e467eb98fc"
        val nbPeople = 2
        val timestamp: Long = 10
        val description = "test"
        val quantity = 10
        val repository: MealRepository = mock(MealRepository::class.java)

        val ms = MealSynchronizer(repository, getRepositoryFolder())
        ms.fromGitToDb(TEST_REPOSITORY_NAME, mealUuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getMeal(mealUuid)
        inOrder.verify(repository).insertMeal(MealRaw(mealUuid, timestamp, nbPeople, description))
        inOrder.verify(repository).insertMealAliment(MealAlimentRaw(alimentUuid, mealUuid, quantity))
        inOrder.verify(repository).insertMealReceipe(MealReceipeRaw(receipeUuid, mealUuid))
    }

    @Test
    fun receipeGitToDb() {
        val receipeUuid = "592bfb6a-0519-4ba6-855c-f4e467eb98fc"
        val nameFr = "testfr"
        val nameEn = "testen"
        val nbPeople = 2
        val stars = 2
        val tagUuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val utensilUuid = "a70ac073-cee8-4e45-b88e-eaeecf186150"
        val stepUuid = "60d1b1a1-f4ab-4d8a-8b8b-dbb248b90318"
        val stepDescription = "step_description"
        val stepDuration = 10
        val stepAlimentUuid = "a7a12c60-1604-48ee-9991-0e4baa08006d"
        val stepAlimentQuantity = 100
        val stepReceipeUuid = "a7a12c60-1604-48ee-9991-0e4baa0800df"

        val repository: ReceipeRepository = mock(ReceipeRepository::class.java)

        val s = ReceipeSynchronizer(repository, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, receipeUuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getReceipe(receipeUuid)
        inOrder.verify(repository).insertReceipe(ReceipeRaw(receipeUuid, nbPeople, stars))
        inOrder.verify(repository).insertReceipeName(ReceipeNameRaw(receipeUuid, "fr", nameFr))
        inOrder.verify(repository).insertReceipeName(ReceipeNameRaw(receipeUuid, "en", nameEn))
        inOrder.verify(repository).insertReceipeTag(ReceipeTagRaw(receipeUuid, tagUuid))
        inOrder.verify(repository).insertReceipeUtensil(ReceipeUtensilRaw(receipeUuid, utensilUuid))
        inOrder.verify(repository).insertReceipeStep(ReceipeStepRaw(stepUuid, receipeUuid, 0, stepDescription, stepDuration))
        inOrder.verify(repository).insertReceipeStepAliment(ReceipeStepAlimentRaw(stepAlimentUuid, stepUuid, stepAlimentQuantity))
        inOrder.verify(repository).insertReceipeStepReceipe(ReceipeStepReceipeRaw(stepReceipeUuid, stepUuid))
    }

    @Test
    fun alimentGitToDb() {
        val alimentUuid = "a7a12c60-1604-48ee-9991-0e4baa08006d"
        val nameFr = "Pomme"
        val nameEn = "Apple"
        val tagUuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val imageUuid = "e56f15a5-29f5-4ba7-8c9a-f750a31198ce"
        val alimentStateUuid = "1f0c4536-b01d-4c7c-9412-d696920ea051"
        val stateUuid = "f33a0e6a-0ad6-4398-a803-3edd8e19987a"
        val measureUuid = "a4a3796e-e478-4864-86f7-bf5d17a603e1"
        val n = 10.0F
        val nutrition = Nutrition(n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n,n)
        val measureQuantity = 10

        val repository: AlimentRepository = mock(AlimentRepository::class.java)

        val s = AlimentSynchronizer(repository, getRepositoryFolder(), object: UuidGeneratorInterface {
            override fun generateUuid(): String = alimentStateUuid
        })

        s.fromGitToDb(TEST_REPOSITORY_NAME, alimentUuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getAliment(alimentUuid)
        inOrder.verify(repository).insertAliment(AlimentRaw(alimentUuid))
        inOrder.verify(repository).insertAlimentName(AlimentNameRaw(alimentUuid, "fr", nameFr))
        inOrder.verify(repository).insertAlimentName(AlimentNameRaw(alimentUuid, "en", nameEn))
        inOrder.verify(repository).insertAlimentImage(AlimentImageRaw(alimentUuid, imageUuid))
        inOrder.verify(repository).insertAlimentTag(AlimentTagRaw(alimentUuid, tagUuid))
        inOrder.verify(repository).insertAlimentState(AlimentStateRaw(alimentStateUuid, alimentUuid, stateUuid, nutrition))
        inOrder.verify(repository).insertAlimentStateMeasure(AlimentStateMeasureRaw(alimentStateUuid, measureUuid, measureQuantity))
    }

    @Test
    fun stateGitToDb() {
        val uuid = "419d4083-4d6d-4436-a383-fc6efd601357"
        val nameFr = "testfr"
        val nameEn = "testen"

        val repository: StateRepository = mock(StateRepository::class.java)
        val s = StateSynchronizer(repository, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getRaw(uuid)
        inOrder.verify(repository).insert(StateRaw(uuid))
        inOrder.verify(repository).insertName(StateNameRaw(uuid, "fr", nameFr))
        inOrder.verify(repository).insertName(StateNameRaw(uuid, "en", nameEn))
    }

    @Test
    fun measureGitToDb() {
        val uuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val nameFr = "testfr"
        val nameEn = "testen"

        val repository: MeasureRepository = mock(MeasureRepository::class.java)
        val s = MeasureSynchronizer(repository, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getRaw(uuid)
        inOrder.verify(repository).insert(MeasureRaw(uuid))
        inOrder.verify(repository).insertName(MeasureNameRaw(uuid, "fr", nameFr))
        inOrder.verify(repository).insertName(MeasureNameRaw(uuid, "en", nameEn))
    }

    @Test
    fun tagGitToDb() {
        val uuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val nameFr = "testfr"
        val nameEn = "testen"

        val repository: TagRepository = mock(TagRepository::class.java)
        val s = TagSynchronizer(repository, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getRaw(uuid)
        inOrder.verify(repository).insert(TagRaw(uuid))
        inOrder.verify(repository).insertName(TagNameRaw(uuid, "fr", nameFr))
        inOrder.verify(repository).insertName(TagNameRaw(uuid, "en", nameEn))
    }

    @Test
    fun utensilGitToDb() {
        val uuid = "a70ac073-cee8-4e45-b88e-eaeecf186150"
        val nameFr = "testfr"
        val nameEn = "testen"

        val repository: UtensilRepository = mock(UtensilRepository::class.java)
        val s = UtensilSynchronizer(repository, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getRaw(uuid)
        inOrder.verify(repository).insert(UtensilRaw(uuid))
        inOrder.verify(repository).insertName(UtensilNameRaw(uuid, "fr", nameFr))
        inOrder.verify(repository).insertName(UtensilNameRaw(uuid, "en", nameEn))
    }
}
