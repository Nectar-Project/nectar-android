package com.realitix.mealassistant

import android.content.Context
import androidx.work.WorkerParameters
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.MealRepository
import com.realitix.mealassistant.repository.ReceipeRepository
import com.realitix.mealassistant.util.EntityType
import com.realitix.mealassistant.util.GitManager
import com.realitix.mealassistant.work.GitRepositoryWorker
import com.realitix.mealassistant.work.synchronizer.MealSynchronizer
import com.realitix.mealassistant.work.synchronizer.ReceipeSynchronizer
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import java.io.File
import java.nio.file.Files


private const val TEST_RESSOURCES_DIR = "src/test/resources/synchronizer"
private const val TEST_DATA_REPOSITORY_URL = "https://github.com/Nectar-Project/nectar-data.git"
private const val TEST_REPOSITORY_NAME = "test_repository"


@RunWith(MockitoJUnitRunner::class)
class SynchronizerUnitTest {
    @Mock
    private lateinit var mockContext: Context


    @Before
    fun initContext() {
        `when`(mockContext.filesDir).thenReturn(File(TEST_RESSOURCES_DIR))
    }

    @Test
    fun parseDataRepository() {
        // Clone repo
        val repositoryName = java.util.UUID.randomUUID().toString()
        val ressourceDir = File(TEST_RESSOURCES_DIR)
        val repositoryFile = File(ressourceDir, repositoryName)
        GitManager(repositoryFile, TEST_DATA_REPOSITORY_URL, null).clone()

        // Loop through each file and parse it
        val workerParameters = mock(WorkerParameters::class.java)
        try {
            for (entityType in EntityType.values()) {
                if (entityType == EntityType.UNKNOW)
                    continue
                Files.list(File(repositoryFile, entityType.folderName).toPath()).forEach {
                    val uuid = it.toFile().name
                    println("Parse path: $it")
                    GitRepositoryWorker(mockContext, workerParameters)
                        .synchronizerMap[entityType]
                        ?.getParseResult(mockContext, repositoryName, uuid)
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

        val ms = MealSynchronizer(mockContext, repository)
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

        val s = ReceipeSynchronizer(mockContext, repository)
        s.fromGitToDb(TEST_REPOSITORY_NAME, receipeUuid)

        val inOrder = inOrder(repository)
        inOrder.verify(repository).getReceipe(receipeUuid)
        inOrder.verify(repository).insertReceipe(ReceipeRaw(receipeUuid, nbPeople, stars))
        inOrder.verify(repository).insertReceipeName(ReceipeNameRaw(receipeUuid, "fr", nameFr))
        inOrder.verify(repository).insertReceipeName(ReceipeNameRaw(receipeUuid, "en", nameEn))
        inOrder.verify(repository).insertReceipeTag(ReceipeTagRaw(receipeUuid, tagUuid))
        inOrder.verify(repository).insertReceipeUtensil(ReceipeUtensilRaw(receipeUuid, utensilUuid))
        inOrder.verify(repository).insertReceipeStep(ReceipeStepRaw(stepUuid, receipeUuid, 1, stepDescription, stepDuration))
        inOrder.verify(repository).insertReceipeStepAliment(ReceipeStepAlimentRaw(stepAlimentUuid, stepUuid, stepAlimentQuantity))
        inOrder.verify(repository).insertReceipeStepReceipe(ReceipeStepReceipeRaw(stepReceipeUuid, stepUuid))
    }
}
