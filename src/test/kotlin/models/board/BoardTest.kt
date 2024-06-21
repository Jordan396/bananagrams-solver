package models.board

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

@DisplayName("BoardTest")
class BoardTest {
    private val outContent = ByteArrayOutputStream()

    @BeforeEach
    fun setUpStreams() {
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun restoreStreams() {
        System.setOut(System.out)
    }

    @Nested
    @DisplayName("add")
    inner class Add {
        @Test
        fun `should add a word to the board when board is empty`() {
            val board = Board
            board.add("hello")
            board.print()

            assertEquals(
                "_______\n" +
                        "|hello|\n" +
                        "-------", outContent.toString()
            )
        }

        @Test
        fun `should successfully add a word to the board in the UP-DOWN position if word is in a viable position (start)`(
        ) {
            val board = Board
            board.add("hello")
            board.add("human", Direction.UP_DOWN, Pair(0, 0))
            board.print()

            assertEquals(
                "_______\n" +
                        "|hello|\n" +
                        "|u    |\n" +
                        "|m    |\n" +
                        "|a    |\n" +
                        "|n    |\n" +
                        "-------", outContent.toString()
            )
        }

        @Test
        fun `should successfully add a word to the board in the UP-DOWN position if word is in a viable position (middle)`(
        ) {
            val board = Board
            board.add("hello")
            board.add("melon", Direction.UP_DOWN, Pair(-2, 2))
            board.print()

            assertEquals(
                "_______\n" +
                        "|  m  |\n" +
                        "|  e  |\n" +
                        "|hello|\n" +
                        "|  o  |\n" +
                        "|  n  |\n" +
                        "-------", outContent.toString()
            )
        }

        @Test
        fun `should successfully add a word to the board in the UP-DOWN position if word is in a viable position (end)`(
        ) {
            val board = Board
            board.add("hello")
            board.add("cello", Direction.UP_DOWN, Pair(-4, 4))
            board.print()

            assertEquals(
                "_______\n" +
                        "|    c|\n" +
                        "|    e|\n" +
                        "|    l|\n" +
                        "|    l|\n" +
                        "|hello|\n" +
                        "-------", outContent.toString()
            )
        }
    }

    @DisplayName("reset")
    @Test
    fun `should reset the board`() {
        val board: Board = Board
        board.add("hello")
        board.reset()
        board.print()

        assertEquals(
            "The board is currently empty.\n", outContent.toString()
        )
    }

}
