package algorithms

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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

    @Nested
    @DisplayName("findLongestWord")
    inner class FindLongestWord {
        @Nested
        @DisplayName("no conditions")
        inner class FindLongestWordNoConditions {
            @Test
            fun `should return the first item in the word map that matches input exactly`() {
                val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
                val longestWord = findLongestWord(wordMap, "eilnst")
                assertEquals(longestWord.first, "listen")
                assertEquals(longestWord.second, 0)
            }

            @Test
            fun `should return the first item in the word map that matches input`() {
                val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
                val longestWord = findLongestWord(wordMap, "eilnstv")
                assertEquals(longestWord.first, "listen")
                assertEquals(longestWord.second, 0)
            }

            @Test
            fun `should return empty string if input is empty`() {
                val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
                val longestWord = findLongestWord(wordMap, "")
                assertEquals(longestWord.first, "")
                assertEquals(longestWord.second, 0)
            }

            @Test
            fun `should return empty string if no matching words`() {
                val wordMap = ProcessedWordMap(TestProcessedWordLoader(testWordMap));
                val longestWord = findLongestWord(wordMap, "lnst")
                assertEquals(longestWord.first, "")
                assertEquals(longestWord.second, 0)
            }
        }

        @Nested
        @DisplayName("with conditions")
        inner class FindLongestWordWithConditions {

        }
    }
}