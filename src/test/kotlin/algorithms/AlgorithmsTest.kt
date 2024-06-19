package algorithms

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import words.ProcessedWordMap
import words.TestProcessedWordLoader
import java.io.OutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class AlgorithmsTest {
    @BeforeEach
    fun setUpStreams() {
        System.setOut(PrintStream(object : OutputStream() {
            override fun write(b: Int) {}
        }))
    }

    @AfterEach
    fun restoreStreams() {
        System.setOut(System.out)
    }

    private val testWordMap: MutableMap<String, MutableList<String>> = mutableMapOf(
        "eilnst" to mutableListOf(
            "listen",
            "enlist",
            "silent",
            "tinsel"
        )
    )

    @Test
    fun `findLongestWord should return the first item in the word map that matches input exactly`() {
        val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "eilnst")
        assertEquals(longestWord, "listen")
        assertEquals(remainingTiles.size, 0)
    }

    @Test
    fun `findLongestWord should return the first item in the word map that matches input`() {
        val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "eilnstv")
        assertEquals(longestWord, "listen")
        assertEquals(remainingTiles, mutableListOf('v'))
    }

    @Test
    fun `findLongestWord should return empty string if input is empty`() {
        val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "")
        assertEquals(longestWord, null)
        assertEquals(remainingTiles.size, 0)
    }

    @Test
    fun `findLongestWord should return empty string if no matching words`() {
        val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
        val (longestWord, remainingTiles) = algorithms.findLongestWord(wordMap, "lnst")
        assertEquals(longestWord, null)
        assertEquals(remainingTiles, mutableListOf('l', 'n', 's', 't'))
    }
}