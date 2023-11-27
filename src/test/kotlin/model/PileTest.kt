package model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class PileTest {

    @Test
    fun `draw valid number of tiles`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val drawnTiles = pile.draw(3)
        assertEquals(3, drawnTiles.size)
        assertEquals(2, pile.getPileSize())
    }

    @Test
    fun `attempt to draw more tiles than are in the pile`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val exception = assertThrows<Exception> {
            pile.draw(6)
        }
        assertEquals("Size of tile pile is smaller than the number of tiles player wishes to draw!", exception.message)
    }

    @Test
    fun `attempt to draw a non-positive number of tiles`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val exception = assertThrows<Exception> {
            pile.draw(-1)
        }
        assertEquals("Player must draw 1 or more tiles!", exception.message)
    }
}
