package com.realitix.mealassistant

import android.content.Context
import com.realitix.mealassistant.util.GitManager
import com.realitix.mealassistant.work.synchronizer.AlimentSynchronizer
import com.realitix.mealassistant.work.synchronizer.MealSynchronizer
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import java.io.File


private const val TEST_RESSOURCES_DIR = "src/test/resources/synchronizer"
private const val TEST_REPOSITORY_URL = "https://github.com/Nectar-Project/nectar-data.git"


@RunWith(MockitoJUnitRunner::class)
class SynchronizerUnitTest {
    companion object {
        val repositoryName = java.util.UUID.randomUUID().toString()
        lateinit var repositoryFile: File

        @BeforeClass @JvmStatic fun setup() {
            val ressourceDir = File(TEST_RESSOURCES_DIR)
            repositoryFile = File(ressourceDir, repositoryName)
            GitManager(repositoryFile, TEST_REPOSITORY_URL, null).clone()
        }

        @AfterClass @JvmStatic fun teardown() {
            repositoryFile.deleteRecursively()
        }
    }
    @Mock
    private lateinit var mockContext: Context


    @Test
    fun parseMeal() {
        `when`(mockContext.filesDir).thenReturn(File(TEST_RESSOURCES_DIR))

        val ms = MealSynchronizer()
        val result = ms.getParseResult(mockContext, repositoryName, "eaa61552-e57c-49fa-a028-4ddc5b8d48c0")
        assertEquals("eaa61552-e57c-49fa-a028-4ddc5b8d48c0", result.uuid)
        assertEquals(2, result.nbPeople)
        assertEquals(1585322751, result.timestamp)
        assertEquals("Un repas de test", result.description)
        for((alimentUuid, quantity) in result.aliments) {
            assertEquals("a7a12c60-1604-48ee-9991-0e4baa08006d", alimentUuid)
            assertEquals(100, quantity)
        }
        for(receipeUuid in result.receipes) {
            assertEquals("592bfb6a-0519-4ba6-855c-f4e467eb98fc", receipeUuid)
        }
    }
}
