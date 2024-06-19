package algorithms

import org.junit.jupiter.api.Test
import words.ProcessedWordMap
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

    @Test
    fun `findLongestWord should return the first item in the word map that matches input exactly`() {
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "eilnst")
        assertEquals(longestWord, "listen")
        assertEquals(remainingTiles.size, 0)
    }

    @Test
    fun `findLongestWord should return the first item in the word map that matches input`() {
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "eilnstv")
        assertEquals(longestWord, "listen")
        assertEquals(remainingTiles, mutableListOf('v'))
    }

    @Test
    fun `findLongestWord should return empty string if input is empty`() {
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "")
        assertEquals(longestWord, null)
        assertEquals(remainingTiles.size, 0)
    }

    @Test
    fun `findLongestWord should return empty string if no matching words`() {
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "lnst")
        assertEquals(longestWord, null)
        assertEquals(remainingTiles, mutableListOf('l', 'n', 's', 't'))
    }
}