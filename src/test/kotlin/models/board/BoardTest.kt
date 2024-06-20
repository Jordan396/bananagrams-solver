package models.board

import models.board.Board
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

@DisplayName("BoardTest")
class BoardTest {
    @Nested
    @DisplayName("add")
    inner class Add {
        private val outContent = ByteArrayOutputStream()

        @BeforeEach
        fun setUpStreams() {
            System.setOut(PrintStream(outContent))
        }

        @AfterEach
        fun restoreStreams() {
            System.setOut(System.out)
        }

        @Test
        fun `should add a word to the board when board is empty`() {
            val board: Board = Board
            board.add("hello")
            board.print()

            assertEquals(
                "_______\n" +
                        "|hello|\n" +
                        "-------", outContent.toString()
            )
        }
    }
}
