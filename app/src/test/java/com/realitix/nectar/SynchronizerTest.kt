package com.realitix.nectar

import android.content.Context
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.repository.*
import com.realitix.nectar.util.*
import com.realitix.nectar.background.synchronizer.*
import org.junit.Assert
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
        GitManager(repositoryFile, TEST_DATA_REPOSITORY_URL, null)

        // Loop through each file and parse it
        val synchronizerMap = mapOf(
            EntityType.STATE to StateSynchronizer(
                StateRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            ),
            EntityType.TAG to TagSynchronizer(
                TagRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            ),
            EntityType.MEASURE to MeasureSynchronizer(
                MeasureRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            ),
            EntityType.ALIMENT to AlimentSynchronizer(
                AlimentRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                AlimentImageRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                AlimentTagRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                AlimentStateRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                AlimentStateMeasureRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder(), UuidGenerator()
            ),
            EntityType.UTENSIL to UtensilSynchronizer(
                UtensilRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            ),
            EntityType.RECEIPE to ReceipeSynchronizer(
                ReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                ReceipeMeasureRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                ReceipeTagRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                ReceipeUtensilRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                ReceipeStepRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                ReceipeStepAlimentStateRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                ReceipeStepReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            ),
            EntityType.MEAL to MealSynchronizer(
                MealRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                MealAlimentRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                MealReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            ),
            EntityType.BOOK to BookSynchronizer(
                BookRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                BookImageRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                BookReceipeRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            ),
            EntityType.STRING_KEY to StringKeySynchronizer(
                StringKeyRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                StringKeyValueRepository(context, GenericCrudRepository.NoTrackEntityUpdater()),
                getRepositoryFolder()
            )
        )

        try {
            for (entityType in synchronizerMap.keys) {
                val folder = File(repositoryFile, entityType.folderName)
                if(folder.exists()) {
                    Files.list(folder.toPath()).forEach {
                        val uuid = it.toFile().name
                        println("Parse path: $it")
                        if (synchronizerMap[entityType] != null) {
                            (synchronizerMap[entityType]
                                ?: error("")).getParseResult(repositoryName, uuid)
                        }
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
        val portions = 2f
        val rMeal = mock(MealRepository::class.java)
        val rMealAliment = mock(MealAlimentRepository::class.java)
        val rMealReceipe = mock(MealReceipeRepository::class.java)

        val ms = MealSynchronizer(rMeal, rMealAliment, rMealReceipe, getRepositoryFolder())
        ms.fromGitToDb(TEST_REPOSITORY_NAME, mealUuid)

        verify(rMeal).get(mealUuid)
        verify(rMeal).insert(MealRaw(mealUuid, timestamp, nbPeople, description))
        verify(rMealAliment).insert(MealAlimentStateRaw(mealUuid, alimentUuid, quantity))
        verify(rMealReceipe).insert(MealReceipeRaw(mealUuid, receipeUuid, portions))
    }

    @Test
    fun receipeGitToDb() {
        val receipeUuid = "592bfb6a-0519-4ba6-855c-f4e467eb98fc"
        val nameUuid = "592bfb6a-0519-4ba6-855c-f4e467eb98fc"
        val portions = 2
        val stars = 2
        val measureUuid = "592bfb6a-0519-4ba6-855c-f4e467eb98fc"
        val measureQuantity = 3f
        val tagUuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val utensilUuid = "a70ac073-cee8-4e45-b88e-eaeecf186150"
        val stepUuid = "60d1b1a1-f4ab-4d8a-8b8b-dbb248b90318"
        val stepDescriptionUuid = "60d1b1a1-f4ab-4d8a-8b8b-dbb248b90318"
        val stepDuration = 10
        val stepAlimentUuid = "a7a12c60-1604-48ee-9991-0e4baa08006d"
        val stepAlimentQuantity = 100
        val stepReceipeUuid = "a7a12c60-1604-48ee-9991-0e4baa0800df"
        val stepReceipeQuantity = 1.5f

        val rReceipe = mock(ReceipeRepository::class.java)
        val rReceipeMeasure = mock(ReceipeMeasureRepository::class.java)
        val rReceipeTag = mock(ReceipeTagRepository::class.java)
        val rReceipeUtensil = mock(ReceipeUtensilRepository::class.java)
        val rReceipeStep = mock(ReceipeStepRepository::class.java)
        val rReceipeStepAliment = mock(ReceipeStepAlimentStateRepository::class.java)
        val rReceipeStepReceipe = mock(ReceipeStepReceipeRepository::class.java)

        val s = ReceipeSynchronizer(rReceipe, rReceipeMeasure, rReceipeTag, rReceipeUtensil,
            rReceipeStep, rReceipeStepAliment, rReceipeStepReceipe, getRepositoryFolder())

        s.fromGitToDb(TEST_REPOSITORY_NAME, receipeUuid)

        verify(rReceipe).get(receipeUuid)
        verify(rReceipe).insert(ReceipeRaw(receipeUuid, nameUuid, portions, stars))
        verify(rReceipeMeasure).insert(ReceipeMeasureRaw(receipeUuid, measureUuid, measureQuantity))
        verify(rReceipeTag).insert(ReceipeTagRaw(receipeUuid, tagUuid))
        verify(rReceipeUtensil).insert(ReceipeUtensilRaw(receipeUuid, utensilUuid))
        verify(rReceipeStep).insert(ReceipeStepRaw(stepUuid, receipeUuid, null, stepDescriptionUuid, stepDuration))
        verify(rReceipeStepAliment).insert(ReceipeStepAlimentStateRaw(stepUuid, stepAlimentUuid, stepAlimentQuantity))
        verify(rReceipeStepReceipe).insert(ReceipeStepReceipeRaw(stepUuid, stepReceipeUuid, stepReceipeQuantity))
    }

    @Test
    fun alimentGitToDb() {
        val alimentUuid = "a7a12c60-1604-48ee-9991-0e4baa08006d"
        val nameUuid = "a7a12c60-1604-48ee-9991-0e4baa08006d"
        val tagUuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val imageUuid = "e56f15a5-29f5-4ba7-8c9a-f750a31198ce"
        val alimentStateUuid = "1f0c4536-b01d-4c7c-9412-d696920ea051"
        val stateUuid = "f33a0e6a-0ad6-4398-a803-3edd8e19987a"
        val measureUuid = "a4a3796e-e478-4864-86f7-bf5d17a603e1"
        val n = 10.0F
        val nutrition = Nutrition.generate(n)
        val measureQuantity = 10

        val rAliment = mock(AlimentRepository::class.java)
        val rAlimentImage = mock(AlimentImageRepository::class.java)
        val rAlimentTag = mock(AlimentTagRepository::class.java)
        val rAlimentState = mock(AlimentStateRepository::class.java)
        val rAlimentStateMeasure = mock(AlimentStateMeasureRepository::class.java)

        val s = AlimentSynchronizer(rAliment, rAlimentImage, rAlimentTag,
            rAlimentState, rAlimentStateMeasure, getRepositoryFolder(), object: UuidGeneratorInterface {
            override fun generateUuid(): String = alimentStateUuid
        })

        s.fromGitToDb(TEST_REPOSITORY_NAME, alimentUuid)

        verify(rAliment).get(alimentUuid)
        verify(rAliment).insert(AlimentRaw(alimentUuid, nameUuid))
        verify(rAlimentImage).insert(AlimentImageRaw(alimentUuid, imageUuid))
        verify(rAlimentTag).insert(AlimentTagRaw(alimentUuid, tagUuid))
        verify(rAlimentState).insert(AlimentStateRaw(alimentStateUuid, alimentUuid, stateUuid, nutrition))
        verify(rAlimentStateMeasure).insert(AlimentStateMeasureRaw(alimentStateUuid, measureUuid, measureQuantity))
    }

    @Test
    fun stateGitToDb() {
        val uuid = "419d4083-4d6d-4436-a383-fc6efd601357"
        val nameUuid = "419d4083-4d6d-4436-a383-fc6efd601357"

        val rState = mock(StateRepository::class.java)
        val s = StateSynchronizer(rState, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        verify(rState).get(uuid)
        verify(rState).insert(StateRaw(uuid, nameUuid))
    }

    @Test
    fun measureGitToDb() {
        val uuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val nameUuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"

        val rMeasure = mock(MeasureRepository::class.java)
        val s = MeasureSynchronizer(rMeasure, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        verify(rMeasure).get(uuid)
        verify(rMeasure).insert(MeasureRaw(uuid, nameUuid))
    }

    @Test
    fun tagGitToDb() {
        val uuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"
        val nameUuid = "1f0ce536-a01d-4c7c-9412-d696920ea051"

        val rTag = mock(TagRepository::class.java)
        val s = TagSynchronizer(rTag, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        verify(rTag).get(uuid)
        verify(rTag).insert(TagRaw(uuid, nameUuid))
    }

    @Test
    fun utensilGitToDb() {
        val uuid = "a70ac073-cee8-4e45-b88e-eaeecf186150"
        val nameUuid = "a70ac073-cee8-4e45-b88e-eaeecf186150"

        val rUtensil = mock(UtensilRepository::class.java)
        val s = UtensilSynchronizer(rUtensil, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        verify(rUtensil).get(uuid)
        verify(rUtensil).insert(UtensilRaw(uuid, nameUuid))
    }

    @Test
    fun stringKeyGitToDb() {
        val uuid = "50b00a4d-39ba-4a40-9362-25edd9a2ac45"
        val valueFr = "Pomme"
        val valueEn = "Apple"

        val rStringKey = mock(StringKeyRepository::class.java)
        val rStringKeyValue = mock(StringKeyValueRepository::class.java)
        val s = StringKeySynchronizer(rStringKey, rStringKeyValue, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        verify(rStringKey).get(uuid)
        verify(rStringKey).insert(StringKeyRaw(uuid))
        verify(rStringKeyValue).insert(StringKeyValueRaw(uuid, "fr", valueFr))
        verify(rStringKeyValue).insert(StringKeyValueRaw(uuid, "en", valueEn))
    }

    @Test
    fun bookGitToDb() {
        val uuid = "e9ccbaf4-6744-4c20-a5a2-97c525ddd94d"
        val nameUuid = "55017f67-8ecd-4446-ac1a-d39c4cc302e1"
        val author = "Auteur test"
        val publishDate: Long = 1585834301
        val imageUuid = "e56f15a5-29f5-4ba7-8c9a-f750a31198ce"
        val receipeUuid = "592bfb6a-0519-4ba6-855c-f4e467eb98fc"

        val rBook = mock(BookRepository::class.java)
        val rBookImage = mock(BookImageRepository::class.java)
        val rBookReceipe = mock(BookReceipeRepository::class.java)
        val s = BookSynchronizer(rBook, rBookImage, rBookReceipe, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        verify(rBook).get(uuid)
        verify(rBook).insert(BookRaw(uuid, nameUuid, author, publishDate))
        verify(rBookImage).insert(BookImageRaw(uuid, imageUuid))
        verify(rBookReceipe).insert(BookReceipeRaw(uuid, receipeUuid))
    }

    @Test
    fun shoppingListGitToDb() {
        val uuid = "e9ccbaf4-6744-4c20-a5a2-97c525ddd94a"
        val beginTimestamp = 10L
        val endTimestamp = 20L
        val alimentStateUuid = "e56f15a5-29f5-4ba7-8c9a-f750a31198ce"
        val weight = 30
        val checked = false

        val rShoppingList = mock(ShoppingListRepository::class.java)
        val rShoppingListAlimentState = mock(ShoppingListAlimentStateRepository::class.java)
        val s = ShoppingListSynchronizer(rShoppingList, rShoppingListAlimentState, getRepositoryFolder())
        s.fromGitToDb(TEST_REPOSITORY_NAME, uuid)

        verify(rShoppingList).get(uuid)
        verify(rShoppingList).insert(ShoppingListRaw(uuid, beginTimestamp, endTimestamp))
        verify(rShoppingListAlimentState).insert(ShoppingListAlimentStateRaw(uuid, alimentStateUuid, weight, checked))
    }
}
