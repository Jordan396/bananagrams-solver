package models.board

import models.board.Board
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("BoardTest")
class BoardTest {
    @Nested
    @DisplayName("add")
    inner class Add {
        @Test
        fun `should add a word to the middle of the board when board is empty`() {
            val board: Board = Board
            board.add("hello")
            board.print()
        }
    }
}
