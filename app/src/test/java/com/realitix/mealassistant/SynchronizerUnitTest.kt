package com.realitix.mealassistant

import com.realitix.mealassistant.work.synchronizer.MealSynchronizer
import org.junit.Test

import org.junit.Assert.*
import org.junit.internal.Classes.getClass
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors


class SynchronizerUnitTest {

    private fun getRessourceFileAsString(filename: String): String {
        val inputStream = SynchronizerUnitTest::class.java.getResourceAsStream(filename)!!
        return inputStream.bufferedReader().use(BufferedReader::readText)
    }

    @Test
    fun parse() {
        val ms = MealSynchronizer()
        val result = ms.parse(getRessourceFileAsString("mealTest.json"))
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
