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

    @Test
    fun `add tiles to existing pile`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val tilesToAdd : MutableList<Char> = mutableListOf()
        tilesToAdd.add('c')
        tilesToAdd.add('d')
        pile.add(tilesToAdd)
        assertEquals(7, pile.getPileSize())
    }

    @Test
    fun `sortThenCombine sorts the tiles in ascending order`() {
        // arrange
        val tilePileConfig = mapOf('z' to 3, 'a' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)

        // act
        val result = pile.sortThenCombine()

        // assert
        assertEquals("aazzz", result)
    }
}
