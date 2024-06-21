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
            val board = Board()
            board.add("hello", mutableListOf('h', 'e', 'l', 'l', 'o'))
            board.print()

            assertEquals(
                "_______\n" +
                        "|hello|\n" +
                        "-------\n", outContent.toString()
            )
        }

        @Test
        fun `should successfully add a word to the board in the UP-DOWN position if word is in a viable position (start)`(
        ) {
            val board = Board()
            board.add("hello", mutableListOf('h', 'e', 'l', 'l', 'o'))
            board.add("human", mutableListOf('u', 'm', 'a', 'n'), Direction.UP_DOWN, Pair(0, 0))
            board.print()

            assertEquals(
                "_______\n" +
                        "|hello|\n" +
                        "|u    |\n" +
                        "|m    |\n" +
                        "|a    |\n" +
                        "|n    |\n" +
                        "-------\n", outContent.toString()
            )
        }

        @Test
        fun `should successfully add a word to the board in the UP-DOWN position if word is in a viable position (middle)`(
        ) {
            val board = Board()
            board.add("hello", mutableListOf('h', 'e', 'l', 'l', 'o'))
            board.add("melon", mutableListOf('m', 'e', 'o', 'n'), Direction.UP_DOWN, Pair(-2, 2))
            board.print()

            assertEquals(
                "_______\n" +
                        "|  m  |\n" +
                        "|  e  |\n" +
                        "|hello|\n" +
                        "|  o  |\n" +
                        "|  n  |\n" +
                        "-------\n", outContent.toString()
            )
        }

        @Test
        fun `should successfully add a word to the board in the UP-DOWN position if word is in a viable position (end)`(
        ) {
            val board = Board()
            board.add("hello", mutableListOf('h', 'e', 'l', 'l', 'o'))
            board.add("cello", mutableListOf('c', 'e', 'l', 'l'), Direction.UP_DOWN, Pair(-4, 4))
            board.print()

            assertEquals(
                "_______\n" +
                        "|    c|\n" +
                        "|    e|\n" +
                        "|    l|\n" +
                        "|    l|\n" +
                        "|hello|\n" +
                        "-------\n", outContent.toString()
            )
        }

        @Nested
        @DisplayName("exceptions")
        inner class AddExceptions {
            @Test
            fun `should not add a word to board if board is empty and there are no tiles provided`(
            ) {
                val board = Board()
                board.add("hello", mutableListOf())

                assertEquals(
                    "Error: Failed to add word - Cannot place tile 'h' at [0, 0] as the tile is not available.\n", outContent.toString()
                )

                outContent.reset()
                board.print()

                assertEquals(
                    "The board is currently empty.\n", outContent.toString()
                )
            }

            @Test
            fun `should not add a word to board if board is empty and there are insufficient tiles provided`(
            ) {
                val board = Board()
                board.add("hello", mutableListOf('h', 'e', 'l' , 'o'))

                assertEquals(
                    "Error: Failed to add word - Cannot place tile 'l' at [0, 3] as the tile is not available.\n", outContent.toString()
                )

                outContent.reset()
                board.print()

                assertEquals(
                    "The board is currently empty.\n", outContent.toString()
                )
            }

            @Test
            fun `should not add a word to board if board is empty and there are excess tiles provided`(
            ) {
                val board = Board()
                board.add("hello", mutableListOf('h', 'e', 'l', 'l' , 'o', 'w'))

                assertEquals(
                    "Error: Failed to add word - After forming the word 'hello', there are some unused tiles remaining.\n", outContent.toString()
                )

                outContent.reset()
                board.print()

                assertEquals(
                    "The board is currently empty.\n", outContent.toString()
                )
            }

            @Test
            fun `should not add a word to board if board is not empty and there are insufficient tiles provided`(
            ) {
                val board = Board()
                board.add("hello", mutableListOf('h', 'e', 'l' ,'l', 'o'))
                board.add("cello", mutableListOf('c', 'e', 'l'), Direction.UP_DOWN, Pair(-4, 4))

                assertEquals(
                    "Error: Failed to add word - Cannot place tile 'l' at [-1, 4] as the tile is not available.\n", outContent.toString()
                )

                outContent.reset()
                board.print()

                assertEquals(
                    "_______\n" +
                            "|hello|\n" +
                            "-------\n", outContent.toString()
                )
            }

            @Test
            fun `should not add a word to board if board is not empty and there are excess tiles provided`(
            ) {
                val board = Board()
                board.add("hello", mutableListOf('h', 'e', 'l' ,'l', 'o'))
                board.add("cello", mutableListOf('c', 'e', 'l', 'l', 'o'), Direction.UP_DOWN, Pair(-4, 4))

                assertEquals(
                    "Error: Failed to add word - After forming the word 'cello', there are some unused tiles remaining.\n", outContent.toString()
                )

                outContent.reset()
                board.print()

                assertEquals(
                    "_______\n" +
                            "|hello|\n" +
                            "-------\n", outContent.toString()
                )
            }
        }
    }

    @DisplayName("reset")
    @Test
    fun `should reset the board`() {
        val board: Board = Board()
        board.add("hello", mutableListOf('h', 'e', 'l', 'l', 'o'))
        board.reset()
        board.print()

        assertEquals(
            "The board is currently empty.\n", outContent.toString()
        )
    }

}
