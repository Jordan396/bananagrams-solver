package algorithms

import words.ProcessedWordMap
import org.junit.jupiter.api.Test
import words.TestProcessedWordLoader
import kotlin.test.assertEquals

class AlgorithmsTest {
    private val testWordMap: MutableMap<String, MutableList<String>> = mutableMapOf(
        "eilnst" to mutableListOf(
            "listen",
            "enlist",
            "silent",
            "tinsel"
        )
    )
    private val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));

//    @Test
//    fun `findLongestWord should find the longest word`() {
//    }

    @Test
    fun `findLongestWord should return empty string if input is empty`() {
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "", mutableListOf())
        assertEquals(longestWord, null)
    }
}